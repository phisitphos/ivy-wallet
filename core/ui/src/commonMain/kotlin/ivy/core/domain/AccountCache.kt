package ivy.core.domain

import arrow.core.partially1
import arrow.core.raise.Raise
import ivy.core.data.AccountId
import ivy.core.data.Transaction
import ivy.core.domain.data.FinancialData
import ivy.core.globalIOScope
import ivy.core.persistence.AccountCachePersistence
import ivy.core.persistence.AccountPersistence
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.data.AccountCache
import ivy.core.persistence.data.ItemChange
import ivy.core.persistence.data.PersistenceError
import ivy.core.util.recoverDoingNothing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class IvyAccountCacheService(
    transactionPersistence: TransactionPersistence,
    accountPersistence: AccountPersistence,
    private val accountCachePersistence: AccountCachePersistence
) : AccountCacheService {
    init {
        globalIOScope.launch {
            transactionPersistence.onItemChange.collect { changes ->
                recover({
                    with(accountCachePersistence) {
                        invalidateAffectedCaches(changes)
                    }
                }) {
                    recoverDoingNothing {
                        accountCachePersistence.deleteAllCaches()
                    }
                }
            }
        }

        globalIOScope.launch {
            accountPersistence.onDeleted.collect { accountIds ->
                recoverDoingNothing {
                    accountCachePersistence.deleteByIds(accountIds)
                }
            }
        }
    }

    context(Raise<PersistenceError>)
    override fun findAccountCache(accountId: AccountId): Flow<FinancialData?> =
        accountCachePersistence.findByAccountId(accountId).distinctUntilChanged()

    context(Raise<PersistenceError>)
    override suspend fun saveAccountCache(accountId: AccountId, cache: FinancialData) {
        accountCachePersistence.save(accountId, cache)
    }
}

interface AccountCacheService {
    context(Raise<PersistenceError>)
    fun findAccountCache(accountId: AccountId): Flow<FinancialData?>

    context(Raise<PersistenceError>)
    suspend fun saveAccountCache(accountId: AccountId, cache: FinancialData)
}

context(AccountCachePersistence, Raise<PersistenceError>)
private suspend fun invalidateAffectedCaches(
    changes: List<ItemChange<Transaction>>
) {
    val affectedCaches = findByAccountIds(changes.flatMap(::affectedAccounts).toSet())
    val needsInvalidation = affectedCaches
        .filter { cache ->
            changes.any(::invalidates.partially1(cache))
        }
        .map { it.accountId }
        .toSet()
        .takeIf { it.isNotEmpty() }

    if (needsInvalidation != null) {
        deleteByIds(needsInvalidation)
    }
}

private fun invalidates(
    accountCache: AccountCache,
    change: ItemChange<Transaction>
): Boolean = with(change) {
    val cacheAccount = accountCache.accountId
    val cacheTime = accountCache.cache.newestTransactionTime
    return when (this) {
        is ItemChange.Creation -> cacheAccount == created.accountId && created.time.isBefore(cacheTime)
        is ItemChange.Deletion -> cacheAccount == deleted.accountId && deleted.time.isBefore(cacheTime)
        is ItemChange.Update -> {
            (cacheAccount == before.accountId && before.time.isBefore(cacheTime)) ||
                (cacheAccount == after.accountId && after.time.isBefore(cacheTime))
        }
    }
}

private fun affectedAccounts(change: ItemChange<Transaction>): List<AccountId> = with(change) {
    when (this) {
        is ItemChange.Creation -> listOf(created.accountId)
        is ItemChange.Deletion -> listOf(deleted.accountId)
        is ItemChange.Update -> listOf(before.accountId, after.accountId)
    }
}

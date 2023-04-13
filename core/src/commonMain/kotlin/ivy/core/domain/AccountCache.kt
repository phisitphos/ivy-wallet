package ivy.core.domain

import arrow.core.raise.Raise
import arrow.core.raise.recover
import ivy.core.data.AccountId
import ivy.core.domain.data.FinancialData
import ivy.core.persistence.AccountCachePersistence
import ivy.core.persistence.AccountPersistence
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class IvyAccountCacheService(
    transactionPersistence: TransactionPersistence,
    accountPersistence: AccountPersistence,
    private val accountCachePersistence: AccountCachePersistence
) : AccountCacheService {
    init {
        transactionPersistence.onSaved.onEach { trns ->
            TODO()
        }
        transactionPersistence.onDeleted.onEach { trns ->
            recover({
                val caches = accountCachePersistence.findByAccountIds(
                    trns.map { it.accountId }.toSet()
                )
                TODO()
            }) {
                // do nothing
            }
        }
        accountPersistence.onDeleted.onEach { accountIds ->
            recover({
                accountCachePersistence.deleteByIds(accountIds)
            }) {
                // do nothing
            }
        }
    }

    context(Raise<PersistenceError>)
    override fun findAccountCache(accountId: AccountId): Flow<FinancialData?> =
        accountCachePersistence.findByAccountId(accountId)

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

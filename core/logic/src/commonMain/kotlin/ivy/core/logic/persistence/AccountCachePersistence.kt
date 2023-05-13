package ivy.core.logic.persistence

import arrow.core.raise.Raise
import ivy.core.domain.data.FinancialData
import ivy.core.logic.persistence.data.AccountCache
import ivy.core.logic.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow

interface AccountCachePersistence {
    context(Raise<PersistenceError>)
    fun findByAccountIds(ids: Set<ivy.core.logic.data.AccountId>): List<AccountCache>

    context(Raise<PersistenceError>)
    fun findByAccountId(accountId: ivy.core.logic.data.AccountId): Flow<FinancialData?>

    context(Raise<PersistenceError>)
    suspend fun save(accountId: ivy.core.logic.data.AccountId, cache: FinancialData)

    context(Raise<PersistenceError>)
    suspend fun deleteByIds(ids: Set<ivy.core.logic.data.AccountId>)

    context(Raise<PersistenceError>)
    suspend fun deleteAllCaches()
}

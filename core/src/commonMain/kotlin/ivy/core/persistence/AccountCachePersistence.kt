package ivy.core.persistence

import arrow.core.raise.Raise
import ivy.core.data.AccountId
import ivy.core.domain.data.FinancialData
import ivy.core.persistence.data.AccountCache
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow

interface AccountCachePersistence {
    context(Raise<PersistenceError>)
    fun findByAccountIds(ids: Set<AccountId>): List<AccountCache>

    context(Raise<PersistenceError>)
    fun findByAccountId(accountId: AccountId): Flow<FinancialData?>

    context(Raise<PersistenceError>)
    suspend fun save(accountId: AccountId, cache: FinancialData)

    context(Raise<PersistenceError>)
    suspend fun deleteByIds(ids: Set<AccountId>)
}

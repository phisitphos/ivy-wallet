package ivy.core.persistence.impl

import arrow.core.raise.Raise
import ivy.core.data.AccountId
import ivy.core.domain.data.FinancialData
import ivy.core.persistence.AccountCachePersistence
import ivy.core.persistence.data.AccountCache
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow

class IvyAccountCachePersistence : AccountCachePersistence {
    context(Raise<PersistenceError>)
    override fun findByAccountIds(ids: Set<AccountId>): List<AccountCache> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override fun findByAccountId(accountId: AccountId): Flow<FinancialData?> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun save(accountId: AccountId, cache: FinancialData) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun deleteByIds(ids: Set<AccountId>) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun deleteAllCaches() {
        TODO("Not yet implemented")
    }
}

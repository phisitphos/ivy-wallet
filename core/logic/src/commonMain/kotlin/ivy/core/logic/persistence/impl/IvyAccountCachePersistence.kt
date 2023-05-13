package ivy.core.logic.persistence.impl

import arrow.core.raise.Raise
import ivy.core.logic.domain.data.FinancialData
import ivy.core.logic.persistence.AccountCachePersistence
import ivy.core.logic.persistence.data.AccountCache
import ivy.core.logic.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow

class IvyAccountCachePersistence : AccountCachePersistence {
    context(Raise<PersistenceError>)
    override fun findByAccountIds(ids: Set<ivy.core.logic.data.AccountId>): List<AccountCache> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override fun findByAccountId(accountId: ivy.core.logic.data.AccountId): Flow<FinancialData?> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun save(accountId: ivy.core.logic.data.AccountId, cache: FinancialData) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun deleteByIds(ids: Set<ivy.core.logic.data.AccountId>) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>)
    override suspend fun deleteAllCaches() {
        TODO("Not yet implemented")
    }
}

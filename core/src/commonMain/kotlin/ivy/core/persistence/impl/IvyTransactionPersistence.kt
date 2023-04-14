package ivy.core.persistence.impl

import arrow.core.raise.Raise
import ivy.core.data.Transaction
import ivy.core.domain.data.TransactionCalcData
import ivy.core.persistence.CalcTrnQuery
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.TrnQuery
import ivy.core.persistence.data.ItemChange
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow

class IvyTransactionPersistence : TransactionPersistence {
    override val onItemChange: Flow<List<ItemChange<Transaction>>>
        get() = TODO("Not yet implemented")

    context(Raise<PersistenceError>) override fun findTransactions(query: TrnQuery): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>) override fun findCalcTransactions(query: CalcTrnQuery): Flow<List<TransactionCalcData>> {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>) override suspend fun save(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>) override suspend fun saveMany(transactions: List<Transaction>) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>) override suspend fun delete(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    context(Raise<PersistenceError>) override suspend fun deleteMany(transactions: List<Transaction>) {
        TODO("Not yet implemented")
    }
}

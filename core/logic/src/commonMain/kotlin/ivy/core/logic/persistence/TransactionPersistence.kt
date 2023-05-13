package ivy.core.logic.persistence

import arrow.core.raise.Raise
import ivy.core.logic.data.AccountId
import ivy.core.logic.data.Transaction
import ivy.core.logic.domain.data.TransactionCalcData
import ivy.core.logic.persistence.data.ItemChange
import ivy.core.logic.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface TransactionPersistence {
    val onItemChange: Flow<List<ItemChange<Transaction>>>

    context(Raise<PersistenceError>)
    fun findTransactions(query: TrnQuery): Flow<List<Transaction>>

    context(Raise<PersistenceError>)
    fun findCalcTransactions(query: CalcTrnQuery): Flow<List<TransactionCalcData>>

    context(Raise<PersistenceError>)
    suspend fun save(transaction: Transaction)

    context(Raise<PersistenceError>)
    suspend fun saveMany(transactions: List<Transaction>)

    context(Raise<PersistenceError>)
    suspend fun delete(transaction: Transaction)

    context(Raise<PersistenceError>)
    suspend fun deleteMany(transactions: List<Transaction>)
}

sealed interface TrnQuery

sealed interface CalcTrnQuery {
    data class ByAccountAfter(
        val accountId: AccountId,
        val after: Instant
    ) : CalcTrnQuery
}

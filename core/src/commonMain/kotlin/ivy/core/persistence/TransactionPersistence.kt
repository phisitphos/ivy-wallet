package ivy.core.persistence

import arrow.core.raise.Raise
import ivy.core.data.AccountId
import ivy.core.data.Transaction
import ivy.core.domain.data.TransactionCalcData
import ivy.core.persistence.data.ItemChange
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow
import java.time.Instant

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
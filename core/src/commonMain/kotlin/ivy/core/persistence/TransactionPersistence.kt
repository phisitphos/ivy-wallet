package ivy.core.persistence

import arrow.core.NonEmptyList
import ivy.core.data.Transaction
import ivy.core.data.TransactionId
import ivy.core.data.time.TimeRange
import ivy.core.domain.data.TransactionCalcData
import kotlinx.coroutines.flow.Flow

interface TransactionPersistence {
    fun transactions(query: TransactionQuery): Flow<List<Transaction>>

    fun calcTransactions(query: TransactionQuery): Flow<List<TransactionCalcData>>
}

sealed interface TransactionQuery {
    data class ByIds(
        val ids: NonEmptyList<TransactionId>
    ) : TransactionQuery

    data class ForPeriod(
        val range: TimeRange,
        val actual: Boolean
    ) : TransactionQuery
}

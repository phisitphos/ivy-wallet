package ivy.core.domain.data

import ivy.core.data.AccountId
import ivy.core.data.MonetaryValue
import ivy.core.data.TransactionFee
import java.time.Instant

sealed interface TransactionCalcData {
    val accountId: AccountId
    val value: MonetaryValue
    val time: Instant
    val settled: Boolean
    val fee: TransactionFee?

    sealed interface OneSided : TransactionCalcData {
        data class Income(
            override val accountId: AccountId,
            override val value: MonetaryValue,
            override val time: Instant,
            override val settled: Boolean,
            override val fee: TransactionFee?
        ) : OneSided

        data class Expense(
            override val accountId: AccountId,
            override val value: MonetaryValue,
            override val time: Instant,
            override val settled: Boolean,
            override val fee: TransactionFee?
        ) : OneSided
    }

    data class Transfer(
        override val accountId: AccountId,
        override val value: MonetaryValue,
        val destinationAccountId: AccountId,
        val destinationValue: MonetaryValue,
        override val time: Instant,
        override val settled: Boolean,
        override val fee: TransactionFee?
    ) : TransactionCalcData
}

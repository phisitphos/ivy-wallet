package ivy.core.domain.data

import ivy.core.data.AccountId
import ivy.core.data.MonetaryValue
import ivy.core.data.TransactionFee
import java.time.Instant

sealed interface TransactionCalcData {
    val value: MonetaryValue
    val time: Instant
    val fee: TransactionFee?

    sealed interface OneSided : TransactionCalcData {
        data class Income(
            override val value: MonetaryValue,
            override val time: Instant,
            override val fee: TransactionFee.OneSided?
        ) : OneSided

        data class Expense(
            override val value: MonetaryValue,
            override val time: Instant,
            override val fee: TransactionFee.OneSided?
        ) : OneSided
    }

    data class Transfer(
        val accountId: AccountId,
        override val value: MonetaryValue,
        val destinationAccountId: AccountId,
        val destinationValue: MonetaryValue,
        override val time: Instant,
        override val fee: TransactionFee.Transfer?
    ) : TransactionCalcData
}

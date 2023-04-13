package ivy.core.domain

import ivy.core.data.AssetCode
import ivy.core.data.MonetaryValue
import ivy.core.data.TransactionFee
import ivy.core.data.primitives.NonNegativeInt
import ivy.core.data.primitives.PositiveDouble
import ivy.core.domain.data.FinancialData
import ivy.core.domain.data.TransactionCalcData
import java.time.Instant

enum class FinancialValueType {
    Income, Expense
}

data class FinancialValue(
    val type: FinancialValueType,
    val value: MonetaryValue
)

/**
 * This function is optimized for performance => that's why it's ugly!
 */
fun foldTransactions(
    transactions: List<TransactionCalcData>,
    interpretTransfer: (TransactionCalcData.Transfer) -> List<FinancialValue>,
    interpretTransferFee: (TransactionFee.Transfer) -> FinancialValue?
): FinancialData {
    val incomes = mutableMapOf<AssetCode, Double>()
    val expenses = mutableMapOf<AssetCode, Double>()
    var incomesCount = 0
    var expensesCount = 0
    var newestTrnTime = Instant.MIN

    fun processFinancialValue(financialValue: FinancialValue) {
        when (financialValue.type) {
            FinancialValueType.Income -> {
                incomes.aggregateByAsset(financialValue.value)
                incomesCount++
            }

            FinancialValueType.Expense -> {
                expenses.aggregateByAsset(financialValue.value)
                expensesCount++
            }
        }
    }

    // Main loop
    transactions.forEach { calcTrn ->
        when (calcTrn) {
            is TransactionCalcData.OneSided.Expense -> {
                expenses.aggregateByAsset(calcTrn.value)
                expensesCount++
            }

            is TransactionCalcData.OneSided.Income -> {
                incomes.aggregateByAsset(calcTrn.value)
                incomesCount++
            }

            is TransactionCalcData.Transfer -> {
                interpretTransfer(calcTrn).forEach(::processFinancialValue)
            }
        }

        when (val fee = calcTrn.fee) {
            is TransactionFee.OneSided -> {
                processFinancialValue(
                    FinancialValue(type = FinancialValueType.Expense, fee.value)
                )
            }

            is TransactionFee.Transfer -> {
                interpretTransferFee(fee)?.let(::processFinancialValue)
            }

            null -> {}
        }

        if (calcTrn.time > newestTrnTime) {
            newestTrnTime = calcTrn.time
        }
    }

    return FinancialData(
        incomes = incomes.mapValues { (_, value) ->
            PositiveDouble(value)
        },
        expenses = expenses.mapValues { (_, value) ->
            PositiveDouble(value)
        },
        incomesCount = NonNegativeInt(incomesCount),
        expensesCount = NonNegativeInt(expensesCount),
        newestTransactionTime = newestTrnTime
    )
}

private fun MutableMap<AssetCode, Double>.aggregateByAsset(
    value: MonetaryValue
) {
    compute(value.assetCode) { _, accumulator ->
        val amount = value.amount.value
        accumulator?.plus(amount) ?: amount
    }
}

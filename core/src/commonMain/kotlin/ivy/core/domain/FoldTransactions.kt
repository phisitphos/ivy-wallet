package ivy.core.domain

import ivy.core.data.AssetCode
import ivy.core.data.primitives.NonNegativeInt
import ivy.core.data.primitives.PositiveDouble
import ivy.core.domain.data.FinancialData
import ivy.core.domain.data.TransactionCalcData
import java.time.Instant

fun foldTransactions(
    transactions: List<TransactionCalcData>,
    interpretTransfer: (TransactionCalcData.Transfer) -> List<TransactionCalcData.OneSided>
): FinancialData {
    val incomes = mutableMapOf<AssetCode, Double>()
    val expenses = mutableMapOf<AssetCode, Double>()
    var incomesCount = 0
    var expensesCount = 0
    var newestTrnTime = Instant.MIN

    transactions.forEach { calcTrn ->
        when (calcTrn) {
            is TransactionCalcData.OneSided.Expense -> {
                expenses.aggregateByAsset(calcTrn)
                expensesCount++
            }

            is TransactionCalcData.OneSided.Income -> {
                incomes.aggregateByAsset(calcTrn)
                incomesCount++
            }

            is TransactionCalcData.Transfer -> {
                interpretTransfer(calcTrn).forEach { oneSided ->
                    when (oneSided) {
                        is TransactionCalcData.OneSided.Expense -> {
                            expenses.aggregateByAsset(oneSided)
                            expensesCount++
                        }

                        is TransactionCalcData.OneSided.Income -> {
                            incomes.aggregateByAsset(oneSided)
                            incomesCount++
                        }
                    }
                }
            }
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
    transaction: TransactionCalcData.OneSided
) {
    compute(transaction.value.assetCode) { _, accumulator ->
        val amount = transaction.value.amount.value
        accumulator?.plus(amount) ?: amount
    }
}

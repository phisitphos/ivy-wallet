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

private data class FinancialDataState(
    val incomes: MutableMap<AssetCode, Double> = mutableMapOf(),
    val expenses: MutableMap<AssetCode, Double> = mutableMapOf(),
    var incomesCount: Int = 0,
    var expensesCount: Int = 0,
    var newestTransactionTime: Instant = Instant.MIN
)

/**
 * This function is optimized for performance => that's why it's ugly!
 */
fun foldTransactions(
    transactions: List<TransactionCalcData>,
    interpretTransfer: (TransactionCalcData.Transfer) -> List<FinancialValue>
): FinancialData {
    val state = FinancialDataState()

    with(state) {
        // Main loop
        transactions.forEach { trn ->
            when (trn) {
                is TransactionCalcData.OneSided.Expense -> processExpense(trn.value)
                is TransactionCalcData.OneSided.Income -> processIncome(trn.value)
                is TransactionCalcData.Transfer -> interpretTransfer(trn)
                    .forEach { processFinancialValue(it) }
            }

            when (val fee = trn.fee) {
                is TransactionFee.OneSided -> processFinancialValue(
                    // every fee is expense
                    FinancialValue(
                        type = FinancialValueType.Expense,
                        value = fee.value
                    )
                )

                is TransactionFee.Transfer, null -> {
                    // transfer fees is processed in interpretTransfer
                }
            }

            // update newest transaction time
            if (trn.time > newestTransactionTime) {
                newestTransactionTime = trn.time
            }
        }
    }

    return FinancialData(
        incomes = state.incomes.mapValues { (_, value) ->
            PositiveDouble(value)
        },
        expenses = state.expenses.mapValues { (_, value) ->
            PositiveDouble(value)
        },
        incomesCount = NonNegativeInt(state.incomesCount),
        expensesCount = NonNegativeInt(state.expensesCount),
        newestTransactionTime = state.newestTransactionTime
    )
}

context(FinancialDataState)
private fun processFinancialValue(financial: FinancialValue) {
    when (financial.type) {
        FinancialValueType.Income -> processIncome(financial.value)
        FinancialValueType.Expense -> processExpense(financial.value)
    }
}

context(FinancialDataState)
private fun processIncome(value: MonetaryValue) {
    incomes.aggregateByAsset(value)
    incomesCount++
}

context(FinancialDataState)
private fun processExpense(value: MonetaryValue) {
    expenses.aggregateByAsset(value)
    expensesCount++
}

private fun MutableMap<AssetCode, Double>.aggregateByAsset(
    value: MonetaryValue
) {
    compute(value.assetCode) { _, accumulator ->
        val amount = value.amount.value
        accumulator?.plus(amount) ?: amount
    }
}

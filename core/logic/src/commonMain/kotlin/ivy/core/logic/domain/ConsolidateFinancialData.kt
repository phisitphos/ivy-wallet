package ivy.core.logic.domain

import arrow.core.None
import arrow.core.Some
import arrow.core.fold
import ivy.core.data.MonetaryValue
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.PositiveDouble
import ivy.core.data.primitives.toNonNegative
import ivy.core.domain.data.ConsolidatedFinancialData
import ivy.core.domain.data.FinancialData
import ivy.core.exchangerates.data.ExchangeRates
import ivy.core.exchangerates.exchange

context(ExchangeRates)
fun consolidateFinancialData(
    financialData: FinancialData,
    outputAsset: ivy.core.logic.data.AssetCode
): ConsolidatedFinancialData {
    return ConsolidatedFinancialData(
        income = financialData.incomes.consolidate(outputAsset),
        expense = financialData.expenses.consolidate(outputAsset),
        incomesCount = financialData.incomesCount,
        expensesCount = financialData.expensesCount
    )
}

context(ExchangeRates)
private fun Map<ivy.core.logic.data.AssetCode, PositiveDouble>.consolidate(
    outputAsset: ivy.core.logic.data.AssetCode
): MonetaryValue {
    val amount = NonNegativeDouble(
        fold(
            initial = 0.0
        ) { acc, (asset, amount) ->
            when (
                val exchanged = exchange(
                    amount = amount.toNonNegative(),
                    from = asset,
                    to = outputAsset
                )
            ) {
                None -> acc
                is Some -> acc + exchanged.value.value
            }
        }
    )
    return MonetaryValue(amount, outputAsset)
}

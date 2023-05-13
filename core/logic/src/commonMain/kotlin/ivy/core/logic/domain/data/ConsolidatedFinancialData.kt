package ivy.core.logic.domain.data

import ivy.core.logic.data.MonetaryValue
import ivy.core.logic.data.primitives.NonNegativeInt

/**
 * A data class representing a financial summary with incomes and expenses converted to the same currency
 * and their respective counts.
 *
 * @property income The total income amount as a monetary value in a single currency.
 * @property expense The total expense amount as a monetary value in a single currency.
 * @property incomesCount The total count of income transactions as an integer.
 * @property expensesCount The total count of expense transactions as an integer.
 */
data class ConsolidatedFinancialData(
    val income: MonetaryValue,
    val expense: MonetaryValue,
    val incomesCount: NonNegativeInt,
    val expensesCount: NonNegativeInt
)

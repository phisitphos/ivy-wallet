package ivy.core.domain.data

import ivy.core.data.AssetCode
import ivy.core.data.primitives.NonNegativeInt
import ivy.core.data.primitives.PositiveDouble
import java.time.Instant

/**
 * A data class representing raw financial data, including incomes and expenses in multiple currencies
 * and their respective counts, as well as the time of the newest transaction for algorithm purposes.
 *
 * @property incomes A map of asset codes to their respective positive income amounts.
 * @property expenses A map of asset codes to their respective positive expense amounts.
 * @property incomesCount The total count of income transactions as a non-negative integer.
 * @property expensesCount The total count of expense transactions as a non-negative integer.
 * @property newestTransactionTime The time of the newest transaction, used for algorithm purposes.
 */
data class FinancialData(
    val incomes: Map<AssetCode, PositiveDouble>,
    val expenses: Map<AssetCode, PositiveDouble>,
    val incomesCount: NonNegativeInt,
    val expensesCount: NonNegativeInt,
    val newestTransactionTime: Instant
)

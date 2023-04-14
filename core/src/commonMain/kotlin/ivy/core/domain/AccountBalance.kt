package ivy.core.domain

import arrow.core.partially1
import arrow.core.raise.Raise
import ivy.core.data.AccountId
import ivy.core.data.AssetCode
import ivy.core.data.primitives.PositiveDouble
import ivy.core.domain.data.FinancialData
import ivy.core.domain.data.TransactionCalcData
import ivy.core.persistence.CalcTrnQuery
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.Instant

context(TransactionPersistence, AccountCacheService, Raise<PersistenceError>)
@OptIn(ExperimentalCoroutinesApi::class)
fun accountFinancialData(
    accountId: AccountId
): Flow<FinancialData> = findAccountCache(accountId).flatMapLatest { cacheData ->
    findCalcTransactions(
        CalcTrnQuery.ByAccountAfter(
            accountId = accountId,
            after = cacheData?.newestTransactionTime ?: Instant.MIN
        )
    ).map(
        ::financialDataFrom.partially1(accountId)
    ).map { afterCacheData ->
        val financialData = cacheData?.plus(afterCacheData) ?: afterCacheData
        if (cacheData != afterCacheData) {
            saveAccountCache(accountId, financialData)
        }
        financialData
    }
}

operator fun FinancialData.plus(other: FinancialData): FinancialData = FinancialData(
    incomes = incomes + other.incomes,
    expenses = expenses + other.expenses,
    incomesCount = incomesCount + other.incomesCount,
    expensesCount = expensesCount + other.expensesCount,
    newestTransactionTime = maxOf(newestTransactionTime, other.newestTransactionTime)
)

operator fun Map<AssetCode, PositiveDouble>.plus(
    other: Map<AssetCode, PositiveDouble>
): Map<AssetCode, PositiveDouble> {
    return buildMap {
        entries.forEach { (key, value) ->
            compute(key) { _, existingValue ->
                existingValue?.plus(value) ?: existingValue
            }
        }

        other.entries.forEach { (key, value) ->
            compute(key) { _, existingValue ->
                existingValue?.plus(value) ?: existingValue
            }
        }
    }
}

fun financialDataFrom(
    accountId: AccountId,
    transactions: List<TransactionCalcData>
): FinancialData = foldTransactions(
    transactions = transactions,
    interpretTransfer = {
        buildList {
            if (it.accountId == accountId) {
                // transfer out
                add(FinancialValue(type = FinancialValueType.Expense, value = it.value))
            }
            if (it.destinationAccountId == accountId) {
                // transfer in
                add(FinancialValue(type = FinancialValueType.Income, value = it.destinationValue))
            }
            if (it.fee != null && it.fee.accountId == accountId) {
                // paying fee
                add(FinancialValue(type = FinancialValueType.Expense, value = it.fee.value))
            }
        }
    }
)

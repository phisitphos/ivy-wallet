package ivy.core.domain

import arrow.core.raise.Raise
import ivy.core.data.Account
import ivy.core.data.AccountId
import ivy.core.domain.data.FinancialData
import ivy.core.domain.data.TransactionCalcData
import ivy.core.persistence.CalcTrnQuery
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.data.PersistenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

context(TransactionPersistence, Raise<PersistenceError>)
fun accountFinancialData(
    account: Account
): Flow<FinancialData> = findCalcTransactions(
    CalcTrnQuery.ByAccount(account.id)
).map {
    financialDataFrom(account.id, it)
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

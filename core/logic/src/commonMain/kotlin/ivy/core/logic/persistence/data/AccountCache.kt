package ivy.core.logic.persistence.data

import ivy.core.logic.data.AccountId
import ivy.core.logic.domain.data.FinancialData

data class AccountCache(
    val accountId: AccountId,
    val cache: FinancialData
)

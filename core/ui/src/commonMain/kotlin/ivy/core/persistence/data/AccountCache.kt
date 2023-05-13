package ivy.core.persistence.data

import ivy.core.data.AccountId
import ivy.core.domain.data.FinancialData

data class AccountCache(
    val accountId: AccountId,
    val cache: FinancialData
)

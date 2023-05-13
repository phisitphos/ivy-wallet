package ivy.core.persistence

import ivy.core.data.AccountId
import kotlinx.coroutines.flow.Flow

interface AccountPersistence {
    val onDeleted: Flow<Set<AccountId>>
}

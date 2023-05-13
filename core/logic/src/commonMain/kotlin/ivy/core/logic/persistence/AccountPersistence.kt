package ivy.core.logic.persistence

import ivy.core.logic.data.AccountId
import kotlinx.coroutines.flow.Flow

interface AccountPersistence {
    val onDeleted: Flow<Set<AccountId>>
}

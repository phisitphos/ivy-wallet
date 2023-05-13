package ivy.core.logic.persistence.impl

import ivy.core.logic.data.AccountId
import ivy.core.logic.persistence.AccountPersistence
import kotlinx.coroutines.flow.Flow

class IvyAccountPersistence : AccountPersistence {
    override val onDeleted: Flow<Set<AccountId>>
        get() = TODO("Not yet implemented")
}

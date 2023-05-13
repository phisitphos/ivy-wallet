package ivy.core.persistence.impl

import ivy.core.data.AccountId
import ivy.core.persistence.AccountPersistence
import kotlinx.coroutines.flow.Flow

class IvyAccountPersistence : AccountPersistence {
    override val onDeleted: Flow<Set<AccountId>>
        get() = TODO("Not yet implemented")
}

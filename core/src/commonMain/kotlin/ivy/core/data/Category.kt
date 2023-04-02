package ivy.core.data

import ivy.core.data.common.Syncable
import ivy.core.data.common.UniqueId
import java.time.Instant
import java.util.*

data class Category(
    override val id: CategoryId,
    override val lastUpdated: Instant,
    override val deleted: Boolean
) : Syncable<CategoryId>

@JvmInline
value class CategoryId(override val value: UUID) : UniqueId

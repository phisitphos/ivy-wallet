package ivy.core.data

import ivy.core.data.common.Syncable
import ivy.core.data.common.UniqueId
import java.time.LocalDateTime
import java.util.*

data class Category(
    override val id: CategoryId,
    override val lastUpdated: LocalDateTime,
    override val deleted: Boolean
) : Syncable<CategoryId>

@JvmInline
value class CategoryId(override val value: UUID) : UniqueId

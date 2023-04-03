package ivy.core.data

import androidx.compose.ui.graphics.Color
import ivy.core.data.common.ItemIconId
import ivy.core.data.common.Reorderable
import ivy.core.data.common.Syncable
import ivy.core.data.common.UniqueId
import ivy.core.data.primitives.NotBlankTrimmedString
import java.time.Instant
import java.util.*

@JvmInline
value class AccountFolderId(override val value: UUID) : UniqueId

data class AccountFolder(
    val name: NotBlankTrimmedString,
    val description: NotBlankTrimmedString?,
    val icon: ItemIconId,
    val color: Color,
    val assetCode: AssetCode,

    override val order: Double,

    // Metadata
    override val id: AccountFolderId,
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Syncable<AccountFolderId>, Reorderable

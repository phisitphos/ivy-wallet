package ivy.core.data

import androidx.compose.ui.graphics.Color
import ivy.core.data.common.ItemIconId
import ivy.core.data.common.Reorderable
import ivy.core.data.common.Syncable
import ivy.core.data.common.UniqueId
import ivy.core.data.primitives.NotBlankTrimmedString
import java.time.Instant
import java.util.*

/**
 * Represents a unique identifier for an AccountFolder.
 * @property value The UUID for the AccountFolderId.
 */
@JvmInline
value class AccountFolderId(override val value: UUID) : UniqueId

/**
 * AccountFolder represents a container for organizing and grouping accounts.
 *
 * @property name The name of the AccountFolder (must be a non-blank trimmed string).
 * @property description An optional description for the AccountFolder (can be a blank or trimmed string).
 * @property icon The unique identifier for the icon associated with the AccountFolder.
 * @property color The color associated with the AccountFolder.
 * @property assetCode The asset code used to display the balance of all accounts within the AccountFolder.
 * @property order A double value representing the order of the AccountFolder in the list.
 * @property id The unique identifier for the AccountFolder.
 * @property deleted A boolean flag indicating whether the AccountFolder is deleted.
 * @property lastUpdated The timestamp of the last update to the AccountFolder.
 */
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

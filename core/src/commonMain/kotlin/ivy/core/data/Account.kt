package ivy.core.data

import androidx.compose.ui.graphics.Color
import ivy.core.data.common.*
import ivy.core.data.primitives.NotBlankTrimmedString
import java.time.Instant
import java.util.*

/**
 * Represents a unique identifier for an Account.
 * @property value The UUID for the AccountId.
 */
@JvmInline
value class AccountId(override val value: UUID) : UniqueId

/**
 * Represents an asset code for an account.
 * @property code The non-blank trimmed string value for the asset code.
 */
@JvmInline
value class AssetCode(val code: NotBlankTrimmedString)

/**
 * Account represents a user's financial account within the application.
 *
 * @property name The name of the Account (must be a non-blank trimmed string).
 * @property description An optional description for the Account (must be a non-blank or trimmed string).
 * @property type The type of the Account, as defined in the AccountType sealed interface.
 * @property assetCode The asset code associated with the Account.
 * @property folderId An optional unique identifier for the folder the Account is in.
 * @property icon An optional unique identifier for the icon associated with the Account.
 * @property color An optional color associated with the Account.
 * @property excluded A boolean flag indicating whether the Account is excluded from statistics.
 * @property archived A boolean flag indicating whether the Account is archived.
 * @property order A double value representing the order of the Account in the list.
 * @property id The unique identifier for the Account.
 * @property deleted A boolean flag indicating whether the Account is deleted.
 * @property lastUpdated The timestamp of the last update to the Account.
 */
data class Account(
    val name: NotBlankTrimmedString,
    val description: NotBlankTrimmedString?,
    val type: AccountType,
    val assetCode: AssetCode,
    val folderId: AccountFolderId?,
    val icon: ItemIconId?,
    val color: Color?,

    val excluded: Boolean,
    override val archived: Boolean,
    override val order: Double,

    // Metadata
    override val id: AccountId,
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Syncable<AccountId>, Archiveable, Reorderable

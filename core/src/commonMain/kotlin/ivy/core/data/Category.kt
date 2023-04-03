package ivy.core.data

import androidx.compose.ui.graphics.Color
import ivy.core.data.common.*
import ivy.core.data.primitives.NotBlankTrimmedString
import java.time.Instant
import java.util.*

/**
 * Represents the type of a Category in Ivy Wallet.
 *
 * The type determines whether the category is associated with income transactions, expense transactions, or both.
 * This information is used in the UI to display the relevant categories when creating or editing transactions.
 */
enum class CategoryType {
    /**
     * Represents a Category that is associated with income transactions.
     */
    Income,

    /**
     * Represents a Category that is associated with expense transactions.
     */
    Expense,

    /**
     * Represents a Category that is associated with both income and expense transactions.
     */
    Both
}

/**
 * Represents a unique identifier for a Category in Ivy Wallet.
 *
 * This value class wraps a [UUID] to provide type-safety and ensure that category identifiers are used consistently across various classes and interfaces.
 *
 * @property value The UUID value of the unique identifier.
 */
@JvmInline
value class CategoryId(override val value: UUID) : UniqueId

/**
 * Represents a Category in Ivy Wallet, used for categorizing transactions.
 *
 * @property icon The icon associated with the category, represented by an [ItemIconId].
 * @property color The display color for the category, represented by a [Color].
 * @property name The user-defined name for the category, represented by a [NotBlankTrimmedString].
 * @property description An optional user-defined description for the category, represented by a [NotBlankTrimmedString].
 * @property type The type of the category, represented by a [CategoryType] (Income, Expense, or Both).
 * @property parentId The optional parent category's [CategoryId], used for organizing categories hierarchically.
 * @property archived Indicates whether the category is archived or not (from the [Archiveable] interface).
 * @property order The display order of the category, relative to other reorderable categories (from the [Reorderable] interface).
 * @property id The unique identifier for the category, represented by a [CategoryId] (from the [Identifiable] and [Syncable] interfaces).
 * @property deleted Indicates whether the category is marked as deleted or not (from the [Deletable] and [Syncable] interfaces).
 * @property lastUpdated The timestamp of the last update of the category (from the [Syncable] interface).
 */
data class Category(
    val icon: ItemIconId,
    val color: Color,
    val name: NotBlankTrimmedString,
    val description: NotBlankTrimmedString?,
    val type: CategoryType,
    val parentId: CategoryId?,

    override val archived: Boolean,
    override val order: Double,

    // Metadata
    override val id: CategoryId,
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Syncable<CategoryId>, Archiveable, Reorderable

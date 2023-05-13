package ivy.core.logic.data.common

/**
 * Indicates that the item can be reordered by the user.
 */
interface Reorderable {
    /**
     * The current order of the item, relative to other reorderable items.
     */
    val order: Double
}

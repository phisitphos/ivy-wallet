package ivy.core.data.common

import ivy.core.data.primitives.NotBlankTrimmedString

/**
 * A unique [String] id representing an Ivy icon.
 * Like "car", "ic_vue_building_bank", "awesomeicon3".
 */
@JvmInline
value class ItemIconId(val id: NotBlankTrimmedString) {
    override fun toString(): String = id.toString()
}

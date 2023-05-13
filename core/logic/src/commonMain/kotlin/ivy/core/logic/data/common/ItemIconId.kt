package ivy.core.logic.data.common

import ivy.core.logic.data.primitives.NotBlankTrimmedString
import kotlin.jvm.JvmInline

/**
 * A unique [String] id representing an Ivy icon.
 * Like "car", "ic_vue_building_bank", "awesomeicon3".
 */
@JvmInline
value class ItemIconId(val id: NotBlankTrimmedString) {
    override fun toString(): String = id.toString()
}

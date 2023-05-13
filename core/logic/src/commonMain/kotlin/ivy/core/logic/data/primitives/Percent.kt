package ivy.core.logic.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.logic.data.primitives.Percent.Companion.fromFloat
import kotlin.jvm.JvmInline

/**
 * Represents a percentage as a float value in the range [[0, 100]].
 * This class provides type-safe operations on percentage values and ensures that only valid percentages are used.
 * To create a valid `Percent` object, use [fromFloat] method.
 *
 * # Example
 * ```
 * val a = Percent(50f)
 * val b = Percent(25f)
 *
 * // Use the comparison operators
 * println(a > b) // true
 *
 * // Use fromFloat method to create valid Percent objects
 * val validPercent = Percent.fromFloat(101f) // This would return None
 * ```
 *
 * @property value The percentage value as a float.
 *
 * # Note
 * It is recommended to use [fromFloat] method to create `Percent` objects, as it ensures type-safety and returns [Option<Percent>] depending on the validity of the input.
 */
@JvmInline
value class Percent private constructor(val value: Float) : Comparable<Percent> {

    operator fun plus(other: Percent): Option<Percent> =
        fromFloat(value + other.value)

    operator fun minus(other: Percent): Option<Percent> =
        fromFloat(value - other.value)

    operator fun times(factor: Float): Option<Percent> =
        fromFloat(value * factor)

    operator fun div(factor: Float): Option<Percent> =
        if (factor != 0f) fromFloat(value / factor) else None

    override fun compareTo(other: Percent): Int = value.compareTo(other.value)

    override fun toString(): String = "$value%"

    companion object {
        fun fromFloat(value: Float): Option<Percent> =
            if (value in 0f..100f) Some(Percent(value)) else None

        fun unsafe(value: Float): Percent = fromFloat(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be between 0 and 100") },
            ifSome = { it }
        )

        operator fun invoke(value: Float): Percent = unsafe(value)
    }
}

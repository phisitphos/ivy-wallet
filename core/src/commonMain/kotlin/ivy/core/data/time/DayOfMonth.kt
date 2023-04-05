package ivy.core.data.time

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.time.DayOfMonth.Companion.fromInt

/**
 * Represents a day of the month as an integer value in the range [[1, 31]], ensuring type-safe operations on day values.
 * Use [fromInt] method to create a valid `DayOfMonth` object in a type-safe manner.
 *
 * # Example
 * ```
 * val day1 = DayOfMonth(5)
 * val day2 = DayOfMonth(10)
 *
 * // Add two DayOfMonth objects
 * val daySum = day1 + day2 // Some(DayOfMonth(15))
 *
 * // Subtract two DayOfMonth objects
 * val dayDifference = day2 - day1 // Some(DayOfMonth(5))
 *
 * // Invalid examples
 * val invalidDay1 = DayOfMonth.fromInt(0) // None
 * val invalidDay2 = DayOfMonth.fromInt(32) // None
 * ```
 *
 * @property value The day of the month as an integer value.
 *
 * # Note
 * It is recommended to use [fromInt] method to create `DayOfMonth` objects, as it ensures type-safety and returns [Option<DayOfMonth>] depending on the validity of the input.
 * Keep in mind that days outside the range [[1, 31]] are considered invalid.
 */
@JvmInline
value class DayOfMonth private constructor(val value: Int) {

    operator fun plus(other: DayOfMonth): Option<DayOfMonth> =
        fromInt(value + other.value)

    operator fun minus(other: DayOfMonth): Option<DayOfMonth> =
        fromInt(value - other.value)

    operator fun times(other: DayOfMonth): Option<DayOfMonth> =
        fromInt(value * other.value)

    operator fun div(other: DayOfMonth): Option<DayOfMonth> =
        if (other.value != 0) fromInt(value / other.value) else None

    operator fun rem(other: DayOfMonth): Option<DayOfMonth> =
        if (other.value != 0) fromInt(value % other.value) else None

    override fun toString(): String = value.toString()

    companion object {
        fun fromInt(value: Int): Option<DayOfMonth> =
            if (value in 1..31) Some(DayOfMonth(value)) else None

        fun unsafe(value: Int): DayOfMonth = fromInt(value).fold(
            ifEmpty = { throw IllegalArgumentException("MonthDate must be between 1 and 31") },
            ifSome = { it }
        )

        operator fun invoke(value: Int): DayOfMonth = unsafe(value)
    }
}

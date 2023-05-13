package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.primitives.PositiveInt.Companion.fromInt

/**
 * Represents a positive integer value greater than 0.
 *
 * This class provides type-safe operations on positive integer values, ensuring that the results are also positive.
 * Supported operations include addition, subtraction, multiplication, division, and modulo. Some operations return an [Option] of [PositiveInt].
 *
 * Use [fromInt] to create instances safely. If the provided value is not positive, it returns `None`.
 *
 * Example usage:
 * ```
 * val a = PositiveInt(1)
 * val b = PositiveInt(2)
 * val result = a + b // result is PositiveInt(3)
 *
 * val invalid = PositiveInt.fromInt(-1) // returns None, as the value is not positive
 * val zero = PositiveInt.fromInt(0) // returns None, as the value is not greater than 0
 * ```
 *
 * @property value The underlying positive integer value.
 */
@JvmInline
value class PositiveInt private constructor(val value: Int) : Comparable<PositiveInt> {

    operator fun plus(other: PositiveInt): PositiveInt =
        unsafe(value + other.value)

    operator fun minus(other: PositiveInt): Option<PositiveInt> =
        fromInt(value - other.value)

    operator fun times(other: PositiveInt): PositiveInt =
        unsafe(value * other.value)

    operator fun div(other: PositiveInt): Option<PositiveInt> =
        if (other.value != 0) fromInt(value / other.value) else None

    operator fun rem(other: PositiveInt): Option<PositiveInt> =
        if (other.value != 0) fromInt(value % other.value) else None

    override fun compareTo(other: PositiveInt): Int = value.compareTo(other.value)

    override fun toString(): String = value.toString()

    companion object {
        fun fromInt(value: Int): Option<PositiveInt> =
            if (value > 0) Some(PositiveInt(value)) else None

        fun unsafe(value: Int): PositiveInt = fromInt(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be positive") },
            ifSome = { it }
        )

        operator fun invoke(value: Int): PositiveInt = unsafe(value)
    }
}

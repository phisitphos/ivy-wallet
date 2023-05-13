package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.primitives.NonNegativeInt.Companion.fromInt

/**
 * Represents a non-negative integer value.
 *
 * This class provides type-safe operations on non-negative integer values, ensuring that the results are also non-negative.
 * Supported operations include addition, subtraction, multiplication, division, and modulo. Some operations return an [Option] of [NonNegativeInt].
 *
 * Use [fromInt] to create instances safely. If the provided value is negative, it returns `None`.
 *
 * Example usage:
 * ```
 * val a = NonNegativeInt(3)
 * val b = NonNegativeInt(2)
 * val result = a + b // result is NonNegativeInt(5)
 *
 * val invalid = NonNegativeInt.fromInt(-1) // returns None, as the value is negative
 * ```
 *
 * @property value The underlying non-negative integer value.
 */
@JvmInline
value class NonNegativeInt private constructor(val value: Int) : Comparable<NonNegativeInt> {

    operator fun plus(other: NonNegativeInt): NonNegativeInt =
        unsafe(value + other.value)

    operator fun minus(other: NonNegativeInt): Option<NonNegativeInt> =
        fromInt(value - other.value)

    operator fun times(other: NonNegativeInt): NonNegativeInt =
        unsafe(value * other.value)

    operator fun div(other: NonNegativeInt): Option<NonNegativeInt> =
        if (other.value != 0) fromInt(value / other.value) else None

    operator fun rem(other: NonNegativeInt): Option<NonNegativeInt> =
        if (other.value != 0) fromInt(value % other.value) else None

    override fun compareTo(other: NonNegativeInt): Int = value.compareTo(other.value)

    override fun toString(): String = value.toString()

    companion object {
        fun fromInt(value: Int): Option<NonNegativeInt> =
            if (value >= 0) Some(NonNegativeInt(value)) else None

        fun unsafe(value: Int): NonNegativeInt = fromInt(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-negative") },
            ifSome = { it }
        )

        operator fun invoke(value: Int): NonNegativeInt = unsafe(value)
    }
}

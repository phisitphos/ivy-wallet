package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.primitives.NonNegativeDouble.Companion.fromDouble

/**
 * Represents a non-negative, finite double value.
 *
 * This class provides type-safe operations on non-negative double values, ensuring that the results are also non-negative and finite.
 * Supported operations include addition, subtraction, multiplication, division, and modulo. All operations return an [Option] of [NonNegativeDouble].
 *
 * Use [fromDouble] to create instances safely. If the provided value is negative, NaN, or infinity, it returns `None`.
 *
 * Example usage:
 * ```
 * val a = NonNegativeDouble(3.0)
 * val b = NonNegativeDouble(2.0)
 * val result = a + b // result is Some(NonNegativeDouble(5.0))
 *
 * val invalid = NonNegativeDouble.fromDouble(-1.0) // returns None, as the value is negative
 * val invalidInfinity = NonNegativeDouble.fromDouble(Double.POSITIVE_INFINITY) // returns None, as the value is infinite
 * ```
 *
 * @property value The underlying non-negative, finite double value.
 */
@JvmInline
value class NonNegativeDouble private constructor(val value: Double) {

    operator fun plus(other: NonNegativeDouble): Option<NonNegativeDouble> =
        fromDouble(value + other.value)

    operator fun minus(other: NonNegativeDouble): Option<NonNegativeDouble> =
        fromDouble(value - other.value)

    operator fun times(other: NonNegativeDouble): Option<NonNegativeDouble> =
        fromDouble(value * other.value)

    operator fun div(other: NonNegativeDouble): Option<NonNegativeDouble> =
        if (other.value != 0.0) fromDouble(value / other.value) else None

    operator fun rem(other: NonNegativeDouble): Option<NonNegativeDouble> =
        if (other.value != 0.0) fromDouble(value % other.value) else None

    override fun toString(): String = value.toString()

    companion object {
        fun fromDouble(value: Double): Option<NonNegativeDouble> =
            if (value >= 0.0 && value.isFinite()) Some(NonNegativeDouble(value)) else None

        fun unsafe(value: Double): NonNegativeDouble = fromDouble(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-negative and finite.") },
            ifSome = { it }
        )

        operator fun invoke(value: Double): NonNegativeDouble = unsafe(value)
    }
}

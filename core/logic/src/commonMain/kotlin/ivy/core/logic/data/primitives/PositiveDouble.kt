package ivy.core.logic.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import ivy.core.logic.data.primitives.PositiveDouble.Companion.fromDouble
import kotlin.jvm.JvmInline

/**
 * Represents a positive double value greater than 0 and finite.
 *
 * This class provides type-safe operations on positive double values, ensuring that the results are also positive.
 * Supported operations include addition, subtraction, multiplication, division, and modulo. Some operations return an [Option] of [PositiveDouble].
 *
 * Use [fromDouble] to create instances safely. If the provided value is not positive or not finite, it returns `None`.
 *
 * Example usage:
 * ```
 * val a = PositiveDouble(1.5)
 * val b = PositiveDouble(2.5)
 * val result = a + b // result is PositiveDouble(4.0)
 *
 * val invalid = PositiveDouble.fromDouble(-1.0) // returns None, as the value is not positive
 * val zero = PositiveDouble.fromDouble(0.0) // returns None, as the value is not greater than 0
 * val infinity = PositiveDouble.fromDouble(Double.POSITIVE_INFINITY) // returns None, as the value is not finite
 * ```
 *
 * @property value The underlying positive, finite double value.
 */
@JvmInline
value class PositiveDouble private constructor(val value: Double) : Comparable<PositiveDouble> {

    operator fun plus(other: PositiveDouble): PositiveDouble =
        fromDouble(value + other.value).getOrElse { unsafe(Double.MAX_VALUE) }

    operator fun minus(other: PositiveDouble): Option<PositiveDouble> =
        fromDouble(value - other.value)

    operator fun times(other: PositiveDouble): PositiveDouble =
        fromDouble(value * other.value).getOrElse { unsafe(Double.MAX_VALUE) }

    operator fun div(other: PositiveDouble): Option<PositiveDouble> =
        if (other.value != 0.0) fromDouble(value / other.value) else None

    operator fun rem(other: PositiveDouble): Option<PositiveDouble> =
        if (other.value != 0.0) fromDouble(value % other.value) else None

    override fun compareTo(other: PositiveDouble): Int = value.compareTo(other.value)

    override fun toString(): String = value.toString()

    companion object {
        fun fromDouble(value: Double): Option<PositiveDouble> =
            if (value > 0.0 && value.isFinite()) Some(PositiveDouble(value)) else None

        fun unsafe(value: Double): PositiveDouble = fromDouble(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be positive and finite.") },
            ifSome = { it }
        )

        operator fun invoke(value: Double): PositiveDouble = unsafe(value)
    }
}

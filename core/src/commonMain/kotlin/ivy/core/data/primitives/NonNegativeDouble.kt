package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class NonNegativeDouble private constructor(val value: Double) {

    operator fun plus(other: NonNegativeDouble): NonNegativeDouble =
        unsafe(value + other.value)

    operator fun minus(other: NonNegativeDouble): Option<NonNegativeDouble> =
        fromDouble(value - other.value)

    operator fun times(other: NonNegativeDouble): NonNegativeDouble =
        unsafe(value * other.value)

    operator fun div(other: NonNegativeDouble): Option<NonNegativeDouble> =
        if (other.value != 0.0) fromDouble(value / other.value) else None

    operator fun rem(other: NonNegativeDouble): Option<NonNegativeDouble> =
        if (other.value != 0.0) fromDouble(value % other.value) else None

    override fun toString(): String = value.toString()

    companion object {
        fun fromDouble(value: Double): Option<NonNegativeDouble> =
            if (value >= 0.0 && value.isFinite()) Some(NonNegativeDouble(value)) else None

        fun unsafe(value: Double): NonNegativeDouble = fromDouble(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-negative") },
            ifSome = { it }
        )

        operator fun invoke(value: Double): NonNegativeDouble = unsafe(value)
    }
}

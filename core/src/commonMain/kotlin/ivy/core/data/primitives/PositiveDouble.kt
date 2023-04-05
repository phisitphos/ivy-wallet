package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class PositiveDouble private constructor(val value: Double) {

    operator fun plus(other: PositiveDouble): Option<PositiveDouble> =
        fromDouble(value + other.value)

    operator fun minus(other: PositiveDouble): Option<PositiveDouble> =
        fromDouble(value - other.value)

    operator fun times(other: PositiveDouble): Option<PositiveDouble> =
        fromDouble(value * other.value)

    operator fun div(other: PositiveDouble): Option<PositiveDouble> =
        if (other.value != 0.0) fromDouble(value / other.value) else None

    operator fun rem(other: PositiveDouble): Option<PositiveDouble> =
        if (other.value != 0.0) fromDouble(value % other.value) else None

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

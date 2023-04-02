package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class PositiveInt private constructor(val value: Int) {

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

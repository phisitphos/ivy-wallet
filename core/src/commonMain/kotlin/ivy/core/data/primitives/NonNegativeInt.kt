package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class NonNegativeInt private constructor(val value: Int) {

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

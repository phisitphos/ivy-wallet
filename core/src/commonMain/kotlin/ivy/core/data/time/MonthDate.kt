package ivy.core.data.time

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class MonthDate private constructor(val value: Int) {

    operator fun plus(other: MonthDate): Option<MonthDate> =
        fromInt(value + other.value)

    operator fun minus(other: MonthDate): Option<MonthDate> =
        fromInt(value - other.value)

    operator fun times(other: MonthDate): Option<MonthDate> =
        fromInt(value * other.value)

    operator fun div(other: MonthDate): Option<MonthDate> =
        if (other.value != 0) fromInt(value / other.value) else None

    operator fun rem(other: MonthDate): Option<MonthDate> =
        if (other.value != 0) fromInt(value % other.value) else None

    override fun toString(): String = value.toString()

    companion object {
        fun fromInt(value: Int): Option<MonthDate> =
            if (value in 1..31) Some(MonthDate(value)) else None

        fun unsafe(value: Int): MonthDate = fromInt(value).fold(
            ifEmpty = { throw IllegalArgumentException("MonthDate must be between 1 and 31") },
            ifSome = { it }
        )

        operator fun invoke(value: Int): MonthDate = unsafe(value)
    }
}

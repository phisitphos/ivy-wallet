package ivy.core.data.time

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

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

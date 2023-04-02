package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class Percent private constructor(val value: Float) {

    operator fun plus(other: Percent): Option<Percent> =
        fromFloat(value + other.value)

    operator fun minus(other: Percent): Option<Percent> =
        fromFloat(value - other.value)

    operator fun times(factor: Float): Option<Percent> =
        fromFloat(value * factor)

    operator fun div(factor: Float): Option<Percent> =
        if (factor != 0f) fromFloat(value / factor) else None

    companion object {
        fun fromFloat(value: Float): Option<Percent> =
            if (value in 0f..100f) Some(Percent(value)) else None

        fun unsafe(value: Float): Percent = fromFloat(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be between 0 and 100") },
            ifSome = { it }
        )

        operator fun invoke(value: Float): Percent = unsafe(value)
    }
}

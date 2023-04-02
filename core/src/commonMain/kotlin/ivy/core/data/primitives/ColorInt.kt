package ivy.core.data.primitives

/**
 * Represents a color as an integer value in the range [0x000000, 0xFFFFFF].
 * This class provides type-safe operations on color values by coercing out-of-range values to the valid color range.
 */
@JvmInline
value class ColorInt private constructor(val value: Int) {

    operator fun plus(other: ColorInt): ColorInt = of(value + other.value)
    operator fun minus(other: ColorInt): ColorInt = of(value - other.value)
    operator fun times(factor: Float): ColorInt = of((value * factor).toInt())
    operator fun div(factor: Float): ColorInt = of((value / factor).toInt())

    companion object {
        private const val MIN_VALUE = 0x000000
        private const val MAX_VALUE = 0xFFFFFF

        private fun of(value: Int): ColorInt = ColorInt(value.coerceIn(MIN_VALUE, MAX_VALUE))
        operator fun invoke(value: Int): ColorInt = of(value)
    }
}

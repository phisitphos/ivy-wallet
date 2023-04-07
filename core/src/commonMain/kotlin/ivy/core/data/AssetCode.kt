package ivy.core.data

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.AssetCode.Companion.fromString
import ivy.core.data.primitives.NotBlankTrimmedString

/**
 * Represents an asset code for an account.
 * Use [fromString] method to create a valid `AssetCode` object.
 *
 * # Example
 * ```
 * val a = AssetCode("usd") // "USD"
 * val b = AssetCode("EUR") // "EUR"
 *
 * // Create a valid AssetCode from a string
 * val validCode = AssetCode.fromString("btc") // Some(AssetCode("BTC"))
 *
 * // Invalid string examples
 * val invalidCode1 = AssetCode.fromString("  ") // None
 * val invalidCode2 = AssetCode.fromString("") // None
 * ```
 *
 * @property code The non-blank trimmed string value for the asset code, automatically converted to uppercase.
 *
 * # Note
 * It is recommended to use [fromString] method to create `AssetCode` objects, as it ensures type-safety and returns [Option<AssetCode>] depending on the validity of the input.
 * Keep in mind that empty or whitespace-only strings are considered invalid.
 */
@JvmInline
value class AssetCode private constructor(val code: NotBlankTrimmedString) {

    override fun toString(): String = code.toString()

    companion object {
        fun fromString(value: String): Option<AssetCode> {
            val trimmedValue = value.trim()
            return if (trimmedValue.isNotBlank()) {
                Some(AssetCode(NotBlankTrimmedString(trimmedValue.uppercase())))
            } else {
                None
            }
        }

        fun unsafe(value: String): AssetCode = fromString(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-blank and trimmed") },
            ifSome = { it }
        )

        operator fun invoke(value: String): AssetCode = unsafe(value)
    }
}

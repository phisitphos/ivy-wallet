package ivy.core.logic.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.logic.data.primitives.NotBlankTrimmedString.Companion.fromString
import kotlin.jvm.JvmInline

/**
 * Represents a string that is not blank and is trimmed, ensuring type-safe operations on string values.
 * Use [fromString] method to create a valid `NotBlankTrimmedString` object.
 *
 * # Example
 * ```
 * val a = NotBlankTrimmedString("  Hello ") // "Hello"
 * val b = NotBlankTrimmedString(" World") // "World"
 *
 * // Concatenate NotBlankTrimmedString objects
 * val c = a + b // "Hello World"
 *
 * // Create a valid NotBlankTrimmedString from a string
 * val validString = NotBlankTrimmedString.fromString("  Example  ") // Some(NotBlankTrimmedString("Example"))
 *
 * // Invalid string examples
 * val invalidString1 = NotBlankTrimmedString.fromString("  ") // None
 * val invalidString2 = NotBlankTrimmedString.fromString("") // None
 * ```
 *
 * @property value The non-blank trimmed string value.
 *
 * # Note
 * It is recommended to use [fromString] method to create `NotBlankTrimmedString` objects, as it ensures type-safety and returns [Option<NotBlankTrimmedString>] depending on the validity of the input.
 * Keep in mind that empty or whitespace-only strings are considered invalid.
 */
@JvmInline
value class NotBlankTrimmedString private constructor(val value: String) {

    operator fun plus(other: NotBlankTrimmedString): NotBlankTrimmedString =
        unsafe(value + other.value)

    fun substring(startIndex: Int, endIndex: Int = value.length): Option<NotBlankTrimmedString> =
        fromString(value.substring(startIndex, endIndex))

    override fun toString(): String = value

    companion object {
        fun fromString(value: String): Option<NotBlankTrimmedString> {
            val trimmedValue = value.trim()
            return if (trimmedValue.isNotBlank()) Some(NotBlankTrimmedString(trimmedValue)) else None
        }

        fun unsafe(value: String): NotBlankTrimmedString = fromString(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-blank and trimmed") },
            ifSome = { it }
        )

        operator fun invoke(value: String): NotBlankTrimmedString = unsafe(value)
    }
}

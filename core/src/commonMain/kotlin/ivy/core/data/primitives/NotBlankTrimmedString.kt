package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class NotBlankTrimmedString private constructor(val value: String) {

    operator fun plus(other: NotBlankTrimmedString): NotBlankTrimmedString =
        unsafe(value + other.value)

    fun substring(startIndex: Int, endIndex: Int = value.length): Option<NotBlankTrimmedString> =
        fromString(value.substring(startIndex, endIndex))

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

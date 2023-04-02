package ivy.core.data.primitives

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

@JvmInline
value class NonBlankTrimmedString private constructor(val value: String) {

    operator fun plus(other: NonBlankTrimmedString): NonBlankTrimmedString =
        unsafe(value + other.value)

    fun substring(startIndex: Int, endIndex: Int = value.length): Option<NonBlankTrimmedString> =
        fromString(value.substring(startIndex, endIndex))

    companion object {
        fun fromString(value: String): Option<NonBlankTrimmedString> {
            val trimmedValue = value.trim()
            return if (trimmedValue.isNotBlank()) Some(NonBlankTrimmedString(trimmedValue)) else None
        }

        fun unsafe(value: String): NonBlankTrimmedString = fromString(value).fold(
            ifEmpty = { throw IllegalArgumentException("Value must be non-blank and trimmed") },
            ifSome = { it }
        )

        operator fun invoke(value: String): NonBlankTrimmedString = unsafe(value)
    }
}

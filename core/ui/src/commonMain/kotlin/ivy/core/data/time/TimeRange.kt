package ivy.core.data.time

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import ivy.core.data.time.TimeRange.Companion.fromInstants
import java.time.Instant

/**
 * Represents a time range as a closed range of [Instant] values, ensuring type-safe operations on time ranges.
 * Use [fromInstants] method to create a valid `TimeRange` object in a type-safe manner.
 *
 * # Example
 * ```
 * val start = Instant.parse("2020-01-01T00:00:00Z")
 * val end = Instant.parse("2020-01-01T01:00:00Z")
 *
 * val validTimeRange = TimeRange.fromInstants(start, end) // Some(TimeRange)
 * val invalidTimeRange = TimeRange.fromInstants(end, start) // None
 * ```
 *
 * # Edge cases
 * Keep in mind that the start `Instant` must be strictly before the end `Instant` to form a valid time range.
 *
 * @property range The closed range of [Instant] values.
 *
 * # Note
 * It is recommended to use [fromInstants] method to create `TimeRange` objects, as it ensures type-safety and returns [Option<TimeRange>] depending on the validity of the input.
 * Make sure the start `Instant` is before the end `Instant`.
 */
@JvmInline
value class TimeRange private constructor(val range: ClosedRange<Instant>) {
    operator fun contains(instant: Instant): Boolean = instant in range

    fun overlaps(other: TimeRange): Boolean {
        return range.start <= other.range.endInclusive && other.range.start <= range.endInclusive
    }

    override fun toString(): String = "${range.start} - ${range.endInclusive}"

    companion object {
        fun fromInstants(start: Instant, end: Instant): Option<TimeRange> =
            if (start.isBefore(end)) Some(TimeRange(start..end)) else None

        fun unsafe(start: Instant, end: Instant): TimeRange = fromInstants(start, end).fold(
            ifEmpty = { throw IllegalArgumentException("Start must be before end") },
            ifSome = { it }
        )

        operator fun invoke(start: Instant, end: Instant): TimeRange = unsafe(start, end)
    }
}

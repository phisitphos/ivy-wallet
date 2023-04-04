package ivy.core.data.time

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import java.time.Instant

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

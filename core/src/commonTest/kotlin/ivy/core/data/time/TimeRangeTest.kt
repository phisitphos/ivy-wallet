package ivy.core.data.time

import arrow.core.Some
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.property.Arb
import io.kotest.property.arbitrary.instant
import io.kotest.property.checkAll
import java.time.Instant

class TimeRangeTest : FreeSpec({
    "[PROPERTY] In ∀ TimeRange the start is before the end" {
        checkAll(Arb.instant(), Arb.instant()) { point1, point2 ->
            val res = TimeRange.fromInstants(point1, point2)

            if (res is Some) {
                val range = res.value.range
                range.endInclusive shouldBeAfter range.start
                range.start shouldBeBefore range.endInclusive
            }
        }
    }

    "[EDGE] When the \"start\" and the \"end\" are the same, the TimeRange is None" {
        val now = Instant.now()
        val res = TimeRange.fromInstants(now, now)

        res.shouldBeNone()
    }
})

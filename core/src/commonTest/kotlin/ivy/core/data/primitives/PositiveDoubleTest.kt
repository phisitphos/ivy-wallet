package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.checkAll

class PositiveDoubleTest : FreeSpec({
    "[PROPERTY] ∀ PositiveDouble is positive and finite" {
        checkAll(Arb.double()) { double ->
            val res = PositiveDouble.fromDouble(double)

            if (res is Some) {
                val resDouble = res.value.value
                resDouble shouldBeGreaterThan 0.0
                resDouble.isFinite().shouldBeTrue()
            }
        }
    }

    "[EDGE] Double.POSITIVE_INFINITY is None" {
        val res = PositiveDouble.fromDouble(Double.POSITIVE_INFINITY)

        res.shouldBeNone()
    }

    "[EDGE] Double.NEGATIVE_INFINITY is None" {
        val res = PositiveDouble.fromDouble(Double.NEGATIVE_INFINITY)

        res.shouldBeNone()
    }
})

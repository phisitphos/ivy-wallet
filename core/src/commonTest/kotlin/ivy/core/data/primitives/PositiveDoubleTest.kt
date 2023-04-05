package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.checkAll
import ivy.core.util.positive

class PositiveDoubleTest : FreeSpec({
    "[PROPERTY] ∀n: ∀ valid PositiveDouble is positive and finite" {
        checkAll(Arb.double()) { double ->
            val res = PositiveDouble.fromDouble(double)

            if (res is Some) {
                val resDouble = res.value.value
                resDouble shouldBeGreaterThan 0.0
                resDouble.isFinite().shouldBeTrue()
            }
        }
    }

    "[EDGE] Double.MAX_VALUE * Double.MAX_VALUE is None" {
        val res = positive(Double.MAX_VALUE) * positive(Double.MAX_VALUE)
        res.shouldBeNone()
    }

    "[EDGE] Double.MAX_VALUE + 1 = Double.MAX_VALUE" {
        val res = positive(Double.MAX_VALUE) + positive(1.0)

        res.shouldBeSome(positive(Double.MAX_VALUE))
    }

    "[EDGE] Double.MAX_VALUE + 100 = Double.MAX_VALUE" {
        val res = positive(Double.MAX_VALUE) + positive(1_000.0)

        res.shouldBeSome(positive(Double.MAX_VALUE))
    }

    "[EDGE] Double.MAX_VALUE + Double.MAX_VALUE is None" {
        val res = positive(Double.MAX_VALUE) + positive(Double.MAX_VALUE)

        res.shouldBeNone()
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

package ivy.core.data.primitives

import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FreeSpec
import ivy.core.util.positive

class PositiveDoubleTest : FreeSpec({
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

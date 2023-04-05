package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.checkAll

class NonNegativeDoubleTest : FreeSpec({
    "[PROPERTY] ∀ NonNegativeDouble is >= 0 and finite." {
        checkAll(Arb.double()) { double ->
            val res = NonNegativeDouble.fromDouble(double)

            if (res is Some) {
                val resDouble = res.value.value
                resDouble shouldBeGreaterThanOrEqual 0.0
                resDouble.isFinite().shouldBeTrue()
            }
        }
    }
})

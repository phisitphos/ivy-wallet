package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class NonNegativeIntTest : FreeSpec({
    "[PROPERTY] ∀n: ∀ valid NonNegativeInt is positive" {
        checkAll(Arb.int()) { int ->
            val res = NonNegativeInt.fromInt(int)

            if (res is Some) {
                res.value.value shouldBeGreaterThanOrEqual 0
            }
        }
    }
})

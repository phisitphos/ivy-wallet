package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class PositiveIntTest : FreeSpec({
    "[PROPERTY] ∀n: ∀ valid PositiveInt is positive" {
        checkAll(Arb.int()) { int ->
            val res = PositiveInt.fromInt(int)

            if (res is Some) {
                res.value.value shouldBeGreaterThan 0
            }
        }
    }
})

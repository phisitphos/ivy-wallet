package ivy.core.data.time

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.beBetween
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class DayOfMonthTest : FreeSpec({
    "[PROPERTY] ∀ DayOfMonth is between 1 and 31" {
        checkAll(Arb.int()) { int ->
            val res = DayOfMonth.fromInt(int)

            if (res is Some) {
                val resValue = res.value.value
                resValue shouldBe beBetween(1, 31)
            }
        }
    }
})

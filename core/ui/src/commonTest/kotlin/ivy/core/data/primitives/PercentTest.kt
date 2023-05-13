package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.floats.between
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.float
import io.kotest.property.checkAll

class PercentTest : FreeSpec({
    "[PROPERTY] ∀ Percent is between 0f and 1f and finite" {
        checkAll(Arb.float()) { float ->
            val percent = Percent.fromFloat(float)

            if (percent is Some) {
                val percentValue = percent.value.value
                percentValue shouldBe between(0f, 100f, tolerance = 0f)
                percentValue.isFinite() shouldBe true
            }
        }
    }
})

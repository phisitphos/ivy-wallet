package ivy.core.data.primitives

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class NotBlankTrimmerStringTest : FreeSpec({
    "[PROPERTY] ∀NotBlankTrimmedString is not blank and trimmed" {
        checkAll(Arb.string()) { string ->
            val res = NotBlankTrimmedString.fromString(string)

            if (res is Some) {
                val resValue = res.value.value
                resValue.isNotBlank().shouldBeTrue()
                resValue.first().isWhitespace().shouldBeFalse()
                resValue.last().isWhitespace().shouldBeFalse()
            }
        }
    }
})

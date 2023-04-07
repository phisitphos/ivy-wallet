package ivy.core.data

import arrow.core.Some
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class AssetCodeTest : FreeSpec({
    "[PROPERTY] ∀ AssetCode is uppercase, not-blank and trimmed" {
        checkAll(Arb.string()) { string ->
            val assetCode = AssetCode.fromString(string)

            if (assetCode is Some) {
                val code = assetCode.value.code.value
                code shouldBe code.uppercase()
                code shouldBe code.trim()
                code.isNotBlank() shouldBe true
            }
        }
    }
})

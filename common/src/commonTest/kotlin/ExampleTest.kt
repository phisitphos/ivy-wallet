import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ExampleTest : FreeSpec({
    "always succeeds" {
        5 shouldBe 5
    }
})
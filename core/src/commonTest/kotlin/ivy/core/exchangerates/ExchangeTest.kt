package ivy.core.exchangerates

import arrow.core.None
import arrow.core.Some
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.datatest.withData
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.assume
import io.kotest.property.checkAll
import ivy.core.data.AssetCode
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.NotBlankTrimmedString
import ivy.core.exchangerates.data.ExchangeRates
import ivy.core.util.*
import kotlin.math.abs

class ExchangeTest : FreeSpec({
    val rates = exchangeRates(
        base = "BGN",
        rates = mapOf(
            "EUR" to 0.5114,
            "USD" to 0.5550,
            "GBP" to 0.4588,
            "BTC" to 0.000024
        )
    )

    "[HAPPY] Valid exchanges" - {
        with(rates) {
            withData(
                nameFn = { (from, to) ->
                    "${from.first} ${from.second} should be ${to.first} ${to.second}"
                },
                row(
                    1.0 to "EUR",
                    1.96 to "BGN"
                ),
                row(
                    100.0 to "EUR",
                    195.54 to "BGN"
                ),
                row(
                    10.0 to "USD",
                    9.21 to "EUR"
                ),
                row(
                    5.0 to "USD",
                    4.13 to "GBP"
                ),
                row(
                    1.0 to "BTC",
                    41_666.67 to "BGN"
                )
            ) { (from, to) ->
                val res = exchange(
                    amount = NonNegativeDouble(from.first),
                    from = AssetCode(NotBlankTrimmedString(from.second)),
                    to = AssetCode(NotBlankTrimmedString(to.second))
                )

                res.map { it.value.round() } shouldBeSome to.first.round()
            }
        }
    }

    "[PROPERTY] Exchange reversibility (isomorphism)" {
        val arbValidAsset = arbitrary {
            rates.rates.keys.random()
        }

        // PROPERTY: 1 BGN -> 0.51 EUR -> 1 BGN
        checkAll(
            arbValidAsset,
            arbValidAsset,
            Arb.nonNegativeDouble()
        ) { asset1, asset2, original ->
            with(rates) {
                val exchanged = exchange(
                    amount = original,
                    from = asset1,
                    to = asset2
                )
                assume(exchanged.isSome())
                val reversed = exchange(
                    amount = (exchanged as Some).value,
                    from = asset2,
                    to = asset1
                )
                reversed.isSome()

                // Use a tolerance value to account for potential rounding errors
                val tolerance = 10.0
                abs(
                    original.value - (reversed as Some).value.value
                ) shouldBeLessThan tolerance
            }
        }
    }

    "[PROPERTY] Non-negative result" {
        checkAll(
            Arb.positiveDouble(),
            Arb.positiveDouble(),
            Arb.nonNegativeDouble()
        ) { rate1, rate2, amount ->
            val newRates = ExchangeRates(
                base = assetCode("base"),
                rates = mapOf(
                    assetCode("base") to positive(1.0),
                    assetCode("2") to rate1,
                    assetCode("3") to rate2
                )
            )

            val res = newRates.exchange(
                amount = amount,
                from = assetCode("2"),
                to = assetCode("3")
            )

            res.shouldBeSome()
            res.value.value shouldBeGreaterThan 0.0
        }
    }

    "[PROPERTY] Exchange with a missing rate fails" {
        val arbValidAsset = arbitrary {
            rates.rates.keys.random()
        }

        val arbInput = arbitrary {
            when (Arb.int(1..3).bind()) {
                1 -> {
                    // from valid, to invalid
                    arbValidAsset.bind() to Arb.assetCode().bind()
                }

                2 -> {
                    // from invalid, to valid
                    Arb.assetCode().bind() to arbValidAsset.bind()
                }

                else -> {
                    // both invalid
                    Arb.assetCode().bind() to Arb.assetCode().bind()
                }
            }
        }

        // PROPERTY: Missing exchange rates returns None
        checkAll(
            arbInput.filter { it.first != it.second },
            Arb.positiveInt()
        ) { (from, to), amount ->
            with(rates) {
                exchange(
                    from = from,
                    to = to,
                    amount = NonNegativeDouble(amount.toDouble())
                ) shouldBe None
            }
        }
    }

    "[EDGE CASE] Disappearing rate ~= 0" {
        // Arrange
        val smallRates = exchangeRates(
            base = "BGN",
            rates = mapOf(
                "y" to 0.00000000000000000000000000000000000000000000000000000000001,
                "x" to 1_000_000_000_000_000.0
            )
        )

        // Act
        val res = smallRates.exchange(
            amount = NonNegativeDouble(1.0),
            from = assetCode("x"),
            to = assetCode("y")
        )

        // Assert
        println(res)
        res.isSome() shouldBe true
        val exchanged = (res as Some).value.value
        exchanged shouldBeLessThan 1.0
        exchanged shouldBeGreaterThan 0.0
        exchanged.round() shouldBe "0.00"
    }
})

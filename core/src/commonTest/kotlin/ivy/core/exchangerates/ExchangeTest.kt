package ivy.core.exchangerates

import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.datatest.withData
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.assume
import io.kotest.property.checkAll
import ivy.core.data.AssetCode
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.NotBlankTrimmedString
import ivy.core.exchangerates.data.ExchangeRates
import ivy.core.util.*

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

    "[HAPPY] Exchanges valid assets (see cases)" - {
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
            Arb.nonNegativeDouble(max = 1_000_000.0)
        ) { asset1, asset2, original ->
            with(rates) {
                val exchanged = exchange(
                    amount = original,
                    from = asset1,
                    to = asset2
                )
                assume(exchanged.isSome())
                exchanged.shouldBeSome()

                val reversed = exchange(
                    amount = exchanged.value,
                    from = asset2,
                    to = asset1
                )

                reversed.shouldBeSome()
                reversed.value.value.round() shouldBe original.value.round()
            }
        }
    }

    "[PROPERTY] Non-negative result" {
        checkAll(
            Arb.positiveDouble(max = 1_000_000_000.0),
            Arb.positiveDouble(max = 1_000_000_000.0),
            Arb.nonNegativeDouble(max = 1_000_000_000.0)
        ) { rate1, rate2, amount ->
            // Given
            val newRates = ExchangeRates(
                base = assetCode("base"),
                rates = mapOf(
                    assetCode("2") to rate1,
                    assetCode("3") to rate2
                )
            )

            // When
            val res = with(newRates) {
                exchange(
                    amount = amount,
                    from = assetCode("2"),
                    to = assetCode("3")
                )
            }

            // Then
            res.shouldBeSome()
            res.value.value shouldBeGreaterThanOrEqual 0.0
        }
    }

    "[PROPERTY] None on missing rate" {
        val arbValidAsset = arbitrary {
            rates.rates.keys.random()
        }

        val arbInput = arbitrary {
            when (Arb.int(1..3).bind()) {
                1 -> {
                    // from valid to invalid
                    arbValidAsset.bind() to Arb.assetCode().bind()
                }

                2 -> {
                    // from invalid to valid
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
            Arb.nonNegativeDouble(min = 10.0)
        ) { (from, to), amount ->
            val res = with(rates) {
                exchange(
                    from = from,
                    to = to,
                    amount = amount
                )
            }

            res.shouldBeNone()
        }
    }

    "[EDGE] Disappearing rate ~= 0" {
        // Given
        val smallRates = exchangeRates(
            base = "BGN",
            rates = mapOf(
                "y" to 0.00000000000000000000000000000000000000000000000000000000001,
                "x" to 1_000_000_000_000_000.0
            )
        )

        // When
        val res = with(smallRates) {
            exchange(
                amount = NonNegativeDouble(1.0),
                from = assetCode("x"),
                to = assetCode("y")
            )
        }

        // Then
        res.shouldBeSome()
        val exchanged = res.value.value
        exchanged shouldBeLessThan 1.0
        exchanged shouldBeGreaterThan 0.0
        exchanged.round() shouldBe "0.00"
    }
})
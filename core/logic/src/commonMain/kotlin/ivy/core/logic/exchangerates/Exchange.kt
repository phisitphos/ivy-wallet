package ivy.core.logic.exchangerates

import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.PositiveDouble
import ivy.core.exchangerates.data.ExchangeRates

/**
 * Exchanges a given [amount] of one asset to another, using the provided exchange rates.
 *
 * @param amount The amount of the asset to exchange, represented as a [NonNegativeDouble].
 * @param from The asset code of the asset to exchange from.
 * @param to The asset code of the asset to exchange to.
 * @return An [Option] containing the exchanged amount as a [NonNegativeDouble] if the exchange is successful, or None otherwise.
 */
context(ExchangeRates)
fun exchange(
    amount: NonNegativeDouble,
    from: ivy.core.logic.data.AssetCode,
    to: ivy.core.logic.data.AssetCode
): Option<NonNegativeDouble> = when {
    from == to -> Some(amount) // no need to exchange
    amount.value == 0.0 -> Some(amount) // no need to exchange
    else -> findRate(from, to).flatMap { rate ->
        NonNegativeDouble.fromDouble(rate.value * amount.value)
    }
}

/**
 * Finds the exchange rate between two assets.
 *
 * @param from The asset code of the asset to exchange from.
 * @param to The asset code of the asset to exchange to.
 * @return An [Option] containing the exchange rate as a [PositiveDouble] if found, or None otherwise.
 *
 * Example usage:
 * ```
 * val exchangeRates = ExchangeRates(base = "BGN", rates = mapOf("EUR" to 0.51, "USD" to 0.56))
 * val rateEURtoUSD = exchangeRates.findRate("EUR", "USD")
 * ```
 */
fun ExchangeRates.findRate(
    from: ivy.core.logic.data.AssetCode,
    to: ivy.core.logic.data.AssetCode
): Option<PositiveDouble> {
    // Helper function to look up the exchange rate for a given asset.
    fun rate(asset: ivy.core.logic.data.AssetCode): Option<PositiveDouble> =
        rates[asset].toOption()

    return when {
        // If the assets are the same, the exchange rate is 1.
        // Example: EUR to EUR -> 1 EUR = 1 EUR
        from == to -> PositiveDouble(1.0).toOption()

        // If the base currency is the same as the from currency, use the rate directly.
        // Example: BGN to EUR with BGN as the base currency and rate BGN to EUR = 0.51 -> 1 BGN = 0.51 EUR
        base == from -> rate(to)

        // If the base currency is the same as the to currency, use the reciprocal of the rate.
        // Example: EUR to BGN with BGN as the base currency and rate BGN to EUR = 0.51 -> 1 EUR = 1 / 0.51 = 1.96 BGN
        base == to -> rate(from).flatMap { rateBaseFrom ->
            PositiveDouble.fromDouble(1.0 / rateBaseFrom.value)
        }

        // If neither the base nor the from currency is the same, calculate the cross rate.
        // Example: EUR to USD with BGN as the base currency, rate BGN to EUR = 0.51, rate BGN to USD = 0.56
        // 1 EUR = 1 / 0.51 * 0.56 = 1.098039 USD
        else -> rate(from).flatMap { rateFrom ->
            rate(to).flatMap { rateTo ->
                PositiveDouble.fromDouble(rateTo.value / rateFrom.value)
            }
        }
    }
}

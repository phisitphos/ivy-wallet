package ivy.core.exchangerates.data

import ivy.core.data.AssetCode
import ivy.core.data.primitives.PositiveDouble

/**
 * Represents exchange rates for a given base asset.
 *
 * The [rates] map indicates the conversion rate between the base asset and other assets.
 * Each entry in the map represents the worth of **1 unit of the asset code** in the **base asset**.
 *
 * Exchange rates of 0 aren't allowed because they can lead to [Double.POSITIVE_INFINITY] or
 * [Double.NaN].
 *
 * @property base The base asset code for the exchange rates.
 * @property rates A map of asset codes to their respective conversion rates, expressed as [PositiveDouble] values.
 *
 * Example:
 * ```
 * {
 *   base: "BGN",
 *   rates: {
 *      "EUR": 0.51,
 *      "USD": 0.56,
 *      "BGN": 1.0
 *   }
 * }
 * ```
 */
data class ExchangeRates(
    val base: AssetCode,
    val rates: Map<AssetCode, PositiveDouble>
)

package ivy.core.util

import ivy.core.data.AssetCode
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.NonNegativeInt
import ivy.core.data.primitives.PositiveDouble
import ivy.core.data.primitives.PositiveInt
import ivy.core.exchangerates.data.ExchangeRates
import java.text.DecimalFormat

fun exchangeRates(
    base: String,
    rates: Map<String, Double>
) = ExchangeRates(
    base = assetCode(base),
    rates = rates.map { (asset, value) ->
        assetCode(asset) to PositiveDouble(value)
    }.toMap()
)

fun nonNegative(double: Double): NonNegativeDouble = NonNegativeDouble(double)
fun nonNegative(int: Int): NonNegativeInt = NonNegativeInt(int)
fun positive(double: Double): PositiveDouble = PositiveDouble(double)
fun positive(int: Int): PositiveInt = PositiveInt(int)

fun assetCode(code: String) = AssetCode(code)

fun Double.round(): String = DecimalFormat("0.00").format(this)

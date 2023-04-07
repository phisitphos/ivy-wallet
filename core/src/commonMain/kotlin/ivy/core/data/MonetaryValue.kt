package ivy.core.data

import ivy.core.data.primitives.PositiveDouble

/**
 * A class representing a monetary or asset value with a specific amount and an associated asset code.
 *
 * @property amount A [PositiveDouble] representing the amount of the monetary or asset value. The amount is guaranteed to be positive.
 * @property assetCode An [AssetCode] representing the associated asset or currency for the monetary or asset value.
 *
 * This class can be used to represent money, assets, and other financial values that have a positive amount and an associated asset code.
 */
data class MonetaryValue(
    val amount: PositiveDouble,
    val assetCode: AssetCode
)

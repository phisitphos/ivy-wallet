package ivy.core.logic.data

import ivy.core.logic.data.primitives.NonNegativeDouble

/**
 * A class representing a monetary or asset value with a specific amount and an associated asset code.
 *
 * @property amount A [NonNegativeDouble] representing the amount of the monetary or asset value.
 * @property assetCode An [AssetCode] representing the associated asset or currency for the monetary or asset value.
 *
 * This class can be used to represent money, assets, and other financial values that have a positive amount and an associated asset code.
 */
data class MonetaryValue(
    val amount: NonNegativeDouble,
    val assetCode: AssetCode
)

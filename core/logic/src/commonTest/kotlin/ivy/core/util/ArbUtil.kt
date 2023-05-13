package ivy.core.util

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import ivy.core.data.primitives.*

fun Arb.Companion.assetCode(): Arb<ivy.core.logic.data.AssetCode> = arbitrary {
    ivy.core.logic.data.AssetCode(Arb.notBlankTrimmedString().bind().value)
}

fun Arb.Companion.notBlankTrimmedString(): Arb<NotBlankTrimmedString> = arbitrary {
    NotBlankTrimmedString(Arb.string().filter { it.isNotBlank() }.bind())
}

fun Arb.Companion.nonNegativeInt(
    min: Int = 0,
    max: Int = Int.MAX_VALUE
): Arb<NonNegativeInt> =
    Arb.int(min = min, max = max).map(NonNegativeInt::unsafe)

fun Arb.Companion.positiveInt(
    min: Int = 1,
    max: Int = Int.MAX_VALUE
): Arb<PositiveInt> =
    Arb.int(min = min, max = max)
        .map(PositiveInt::unsafe)

fun Arb.Companion.nonNegativeDouble(
    min: Double = 0.0,
    max: Double = Double.MAX_VALUE
): Arb<NonNegativeDouble> =
    Arb.double(min = min, max = max)
        .filter { it.isFinite() }
        .map(NonNegativeDouble::unsafe)

fun Arb.Companion.positiveDouble(
    min: Double = 0.000000000000000000000001,
    max: Double = Double.MAX_VALUE
): Arb<PositiveDouble> =
    Arb.double(min = min, max = max)
        .filter { it.isFinite() }
        .map(PositiveDouble::unsafe)

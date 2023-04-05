package ivy.core.util

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import ivy.core.data.AssetCode
import ivy.core.data.primitives.*

fun Arb.Companion.assetCode(): Arb<AssetCode> = arbitrary {
    AssetCode(Arb.notBlankTrimmedString().bind())
}

fun Arb.Companion.notBlankTrimmedString(): Arb<NotBlankTrimmedString> = arbitrary {
    NotBlankTrimmedString(Arb.string().filter { it.isNotBlank() }.bind())
}

fun Arb.Companion.nonNegativeInt(max: Int = Int.MAX_VALUE): Arb<NonNegativeInt> =
    Arb.int(min = 0, max = max).map(NonNegativeInt::unsafe)

fun Arb.Companion.positiveInt(max: Int = Int.MAX_VALUE): Arb<PositiveInt> =
    Arb.int(min = 1, max = max)
        .map(PositiveInt::unsafe)

fun Arb.Companion.nonNegativeDouble(max: Double = Double.MAX_VALUE): Arb<NonNegativeDouble> =
    Arb.double(min = 0.0, max = max)
        .filter { it.isFinite() }
        .map(NonNegativeDouble::unsafe)

fun Arb.Companion.positiveDouble(max: Double = Double.MAX_VALUE): Arb<PositiveDouble> =
    Arb.double(min = 0.000000000000000000000001, max = max)
        .filter { it.isFinite() }
        .map(PositiveDouble::unsafe)

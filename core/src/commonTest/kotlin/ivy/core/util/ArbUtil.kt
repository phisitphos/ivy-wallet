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

fun Arb.Companion.nonNegativeInt(): Arb<NonNegativeInt> =
    Arb.int(min = 0).map(NonNegativeInt::invoke)

fun Arb.Companion.nonNegativeDouble(): Arb<NonNegativeDouble> =
    Arb.double(min = 0.0).map(NonNegativeDouble::invoke)

fun Arb.Companion.positiveInt(): Arb<PositiveInt> =
    Arb.int(min = 1).map(PositiveInt::invoke)

fun Arb.Companion.positiveDouble(): Arb<PositiveDouble> =
    Arb.double(min = 0.000001).map(PositiveDouble::invoke)

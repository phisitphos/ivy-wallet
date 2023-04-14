package ivy.core.util

import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.recover
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
@RaiseDSL
inline fun <Error, A> recoverDoingNothing(
    @BuilderInference block: Raise<Error>.() -> A
): A? = recover(block) { null }

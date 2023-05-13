package ivy.core.logic

import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.recover
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
@RaiseDSL
inline fun <Error, A> recoverDoingNothing(
    @BuilderInference block: Raise<Error>.() -> A
): A? = recover(block) { null }

fun uuid(): Uuid = uuid4()
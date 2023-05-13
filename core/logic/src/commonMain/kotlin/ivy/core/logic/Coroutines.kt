package ivy.core.logic

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val globalIOScope = CoroutineScope(
    Dispatchers.Default + SupervisorJob() + CoroutineName("IO scope")
)

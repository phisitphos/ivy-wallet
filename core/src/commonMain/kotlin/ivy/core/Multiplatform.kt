package ivy.core

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val globalIOScope = CoroutineScope(
    Dispatchers.IO + SupervisorJob() + CoroutineName("IO scope")
)

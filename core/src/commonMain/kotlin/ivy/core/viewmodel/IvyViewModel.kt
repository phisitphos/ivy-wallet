package ivy.core.viewmodel

import androidx.compose.runtime.Composable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class IvyViewModel<UiState, Event> {
    @Composable
    abstract fun uiState(): UiState

    protected abstract suspend fun handleEvent(event: Event)

    private val events = MutableSharedFlow<Event>(replay = 0)
    protected val viewModelScope = CoroutineScope(
        SupervisorJob() + CoroutineName("Ivy viewModelScope")
    )

    init {
        viewModelScope.launch {
            events.collect(::handleEvent)
        }
    }

    fun onEvent(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    fun cancel() {
        viewModelScope.cancel("Canceling IvyViewModel")
    }
}

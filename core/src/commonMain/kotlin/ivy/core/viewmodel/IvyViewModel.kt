package ivy.core.viewmodel

import androidx.compose.runtime.Composable
import ivy.core.ImmutableDI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import org.kodein.di.DI
import org.kodein.di.DIAware

abstract class IvyViewModel<UiState, Event>(di: ImmutableDI) : DIAware {
    override val di: DI = di.di

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

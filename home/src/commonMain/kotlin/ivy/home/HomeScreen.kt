package ivy.home

import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen

object HomeScreen : Screen {
    @Stable
    data class State(
        val screenName: String,
        val people: List<String>,
        val sinkEvent: (Event) -> Unit
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        object ButtonClick : Event
    }
}

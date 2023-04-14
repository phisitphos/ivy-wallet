package ivy.core.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ivy.core.IvyWalletDI
import ivy.core.viewModel

data class DemoState(
    val counter: Int
)

sealed interface DemoEvent {
    object Increment : DemoEvent
    object Decrement : DemoEvent
}

context(IvyWalletDI)
class DemoViewModel : IvyViewModel<DemoState, DemoEvent>() {
    private var counter by mutableStateOf(0)

    @Composable
    override fun uiState(): DemoState = DemoState(
        counter = counter
    )

    override suspend fun handleEvent(event: DemoEvent) {
        when (event) {
            DemoEvent.Increment -> counter++
            DemoEvent.Decrement -> counter--
        }
    }
}

context(IvyWalletDI)
@Composable
fun DemoScreen(
    viewModel: DemoViewModel = viewModel("demo", ::DemoViewModel)
) {
    val state = viewModel.uiState()

    Column {
        Text(text = "Counter: ${state.counter}")
        Row {
            Button(onClick = {
                viewModel.onEvent(DemoEvent.Increment)
            }) {
                Text("Increment")
            }
            Spacer(Modifier.width(16.dp))
            Button(onClick = {
                viewModel.onEvent(DemoEvent.Decrement)
            }) {
                Text("Decrement")
            }
        }
    }
}

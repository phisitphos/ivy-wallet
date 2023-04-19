package ivy.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ivy.core.viewModel

@Composable
fun HomeScreen() {
    val viewModel = viewModel { HomeViewModel(it) }
    UI(viewModel.uiState(), viewModel::onEvent)
}

@Composable
private fun UI(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello, ${state.screenName}!")
        Button(onClick = { onEvent(HomeEvent.ButtonClick) }) {
            Text("Click me")
        }
        for ((index, person) in state.people.withIndex()) {
            Text("Person $index: $person")
        }
    }
}

package ivy.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
    event: (HomeEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello, ${state.screenName}!")
    }
}

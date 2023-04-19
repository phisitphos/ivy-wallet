package ivy.home

import androidx.compose.runtime.Composable
import ivy.core.viewmodel.IvyViewModel

class HomeViewModel : IvyViewModel<HomeState, HomeEvent>() {
    @Composable
    override fun uiState(): HomeState {
        return HomeState(
            greeting = "Hello"
        )
    }

    override suspend fun handleEvent(event: HomeEvent) {
        // TODO:
    }
}

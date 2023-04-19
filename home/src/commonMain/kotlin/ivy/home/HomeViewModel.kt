package ivy.home

import androidx.compose.runtime.Composable
import ivy.Database
import ivy.core.ImmutableDI
import ivy.core.persistence.data.AccountCache
import ivy.core.viewmodel.IvyViewModel
import org.kodein.di.instance

class HomeViewModel(di: ImmutableDI) : IvyViewModel<HomeState, HomeEvent>(di) {
    private val database by instance<Database>()
    private val accountCache by instance<AccountCache>()

    @Composable
    override fun uiState(): HomeState {
        return HomeState(
            screenName = "Home"
        )
    }

    override suspend fun handleEvent(event: HomeEvent) {
        database.personQueries.insert(1, "Test")
    }
}

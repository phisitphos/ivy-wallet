package ivy.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import ivy.Database
import ivy.core.ImmutableDI
import ivy.core.persistence.data.AccountCache
import ivy.core.temp.peopleFlow
import ivy.core.viewmodel.IvyViewModel
import ivy.sqldelight.HockeyPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.kodein.di.instance

class HomeViewModel(di: ImmutableDI) : IvyViewModel<HomeState, HomeEvent>(di) {
    private val database by instance<Database>()
    private val accountCache by instance<AccountCache>()

    @Composable
    override fun uiState(): HomeState {
        return HomeState(
            screenName = "Home",
            people = people()
        )
    }

    @Composable
    private fun people(): List<String> {
        val people by remember {
            with(database) {
                peopleFlow().map { it.map(HockeyPlayer::full_name) }
            }
        }.collectAsState(emptyList())
        return people
    }

    override suspend fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ButtonClick -> {
                withContext(Dispatchers.IO) {
                    database.personQueries.insert(1, "Test")
                }
            }
        }
    }
}

package ivy.home

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import ivy.core.di.AppScope
import javax.inject.Inject

@CircuitInject(HomeScreen::class, AppScope::class)
class HomePresenter @Inject constructor() : Presenter<HomeScreen.State> {
    @Composable
    override fun present(): HomeScreen.State {
        return HomeScreen.State(
            screenName = "Home",
            people = listOf()
        ) { event ->
            when (event) {
                HomeScreen.Event.ButtonClick -> TODO()
            }
        }
    }
}

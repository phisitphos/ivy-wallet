package ivy.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import ivy.core.di.AppScope

@CircuitInject(HomeScreen::class, AppScope::class)
@Composable
fun Home(state: HomeScreen.State) {
    Text("Hello, ${state.screenName}!")
}

package ivy.root

import androidx.compose.runtime.Composable
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.*
import ivy.core.ui.IvyUI
import ivy.home.HomeScreen

val config = CircuitConfig.Builder().build()

@Composable
fun IvyWalletApp() {
    IvyUI {
        CircuitCompositionLocals(config) {
            val backstack = rememberSaveableBackStack { push(HomeScreen) }
            val navigator = rememberCircuitNavigator(backstack) {
                // TODO: onRootPop
            }
            NavigableCircuitContent(navigator, backstack)
        }
    }
}

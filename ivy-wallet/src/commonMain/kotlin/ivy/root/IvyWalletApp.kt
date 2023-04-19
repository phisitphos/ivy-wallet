package ivy.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ivy.core.navigation.HomeScreen
import ivy.core.navigation.NavHost
import ivy.core.navigation.navigateTo
import ivy.core.ui.IvyUI
import ivy.home.HomeScreen

@Composable
fun IvyWalletApp() {
    LaunchedEffect(Unit) {
        navigateTo(HomeScreen)
    }

    IvyUI {
        NavHost(
            initialUI = {}
        ) { screen ->
            when (screen) {
                is HomeScreen -> HomeScreen()
                else -> error("Unsupported screen: $screen - fix it!")
            }
        }
    }
}

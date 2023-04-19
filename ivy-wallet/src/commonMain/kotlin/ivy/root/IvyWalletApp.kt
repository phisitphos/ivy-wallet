package ivy.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import ivy.core.ImmutableDI
import ivy.core.LocalDI
import ivy.core.navigation.NavHost
import ivy.core.navigation.navigateTo
import ivy.core.navigation.screen.HomeScreen
import ivy.core.ui.IvyUI
import ivy.home.HomeScreen

@Composable
fun IvyWalletApp(
    di: ImmutableDI
) {
    LaunchedEffect(Unit) {
        navigateTo(HomeScreen)
    }

    CompositionLocalProvider(
        LocalDI provides di
    ) {
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
}

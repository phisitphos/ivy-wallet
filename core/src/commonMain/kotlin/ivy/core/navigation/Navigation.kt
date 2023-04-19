package ivy.core.navigation

import androidx.compose.runtime.*

interface Screen

private val backstack = mutableStateListOf<Screen>()

@Composable
fun NavHost(
    initialUI: @Composable () -> Unit,
    screenUI: @Composable (currentScreen: Screen) -> Unit
) {
    val currentScreen by remember(backstack) {
        derivedStateOf { backstack.lastOrNull() }
    }
    currentScreen?.let { screenUI(it) } ?: initialUI()
}

fun navigateTo(screen: Screen) {
    backstack.add(screen)
}

fun navigateBack() {
    backstack.removeLastOrNull()
}

fun clearBackstack() {
    backstack.clear()
}

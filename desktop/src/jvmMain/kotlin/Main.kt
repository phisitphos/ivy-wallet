import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.App
import ivy.home.HomeScreen


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column {
            App()
            HomeScreen(platform = "Desktop")
        }
    }
}

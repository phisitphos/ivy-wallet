import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.common.App
import ivy.home.HomeScreen


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column {
            App()
            HomeScreen()
        }
    }
}

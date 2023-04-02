import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.App
import ivy.core.persistence.DriverFactory
import ivy.core.persistence.playWithDb
import ivy.home.HomeScreen


fun main() = application {
    var people by remember {
        mutableStateOf(emptyList<String>())
    }
    LaunchedEffect(Unit) {
        people = playWithDb(DriverFactory())
    }

    Window(onCloseRequest = ::exitApplication) {
        Column {
            App()
            HomeScreen(platform = "Desktop")
            for(person in people) {
                Text(text = person)
            }
        }
    }
}

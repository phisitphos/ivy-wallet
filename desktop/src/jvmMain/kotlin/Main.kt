import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.common.App
import ivy.common.persistence.DriverFactory
import ivy.common.persistence.createDatabase
import ivy.home.HomeScreen


fun main() = application {
    var people by remember {
        mutableStateOf(emptyList<String>())
    }
    LaunchedEffect(Unit) {
        val db = createDatabase(DriverFactory())
        db.personQueries.insert(1, "John")
        db.personQueries.insert(2, "Iliyan")
        people = db.personQueries.selectAll().executeAsList().map { it.full_name }
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

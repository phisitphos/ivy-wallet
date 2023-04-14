import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.App
import ivy.core.ivyWalletDI
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.home.HomeScreen

val sqlDriver by lazy { SQLDelightDriverFactory().createDriver() }

fun main() {
    ivyWalletDI(sqlDriver = sqlDriver) {
        application {
            Window(onCloseRequest = ::exitApplication) {
                Column {
                    App()
                    HomeScreen(platform = "Desktop")
                }
            }
        }
    }
}

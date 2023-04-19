import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp

val sqlDriver by lazy { SQLDelightDriverFactory().createDriver() }

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            IvyWalletApp()
        }
    }
}

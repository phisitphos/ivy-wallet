import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.ImmutableDI
import ivy.core.coreDi
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp

val di = ImmutableDI(
    di = coreDi(
        sqlDriverProvider = {
            SQLDelightDriverFactory().createDriver()
        }
    )
)

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            IvyWalletApp(di)
        }
    }
}

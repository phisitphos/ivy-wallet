import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ivy.core.ImmutableDI
import ivy.core.coreDi
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val di = ImmutableDI(
    DI {
        bindSingleton { SQLDelightDriverFactory().createDriver() }
        extend(coreDi)
    }
)

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            IvyWalletApp(di)
        }
    }
}

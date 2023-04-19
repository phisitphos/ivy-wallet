package ivy.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ivy.core.ImmutableDI
import ivy.core.coreDi
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp
import org.kodein.di.DI
import org.kodein.di.bindSingleton

class MainActivity : AppCompatActivity() {

    private val di = ImmutableDI(
        DI {
            bindSingleton { SQLDelightDriverFactory(applicationContext).createDriver() }
            extend(coreDi)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IvyWalletApp(di)
        }
    }
}

package ivy.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ivy.core.ImmutableDI
import ivy.core.coreDi
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp

class MainActivity : AppCompatActivity() {

    private val di = ImmutableDI(
        di = coreDi(
            sqlDriverProvider = {
                SQLDelightDriverFactory(applicationContext).createDriver()
            }
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IvyWalletApp(di)
        }
    }
}

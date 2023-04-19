package ivy.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.root.IvyWalletApp

class MainActivity : AppCompatActivity() {

    val sqlDriver by lazy { SQLDelightDriverFactory(applicationContext).createDriver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IvyWalletApp()
        }
    }
}

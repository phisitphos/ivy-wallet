package ivy.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import ivy.core.ivyWalletDI
import ivy.core.persistence.setup.SQLDelightDriverFactory
import ivy.core.temp.App
import ivy.home.HomeScreen

class MainActivity : AppCompatActivity() {

    val sqlDriver by lazy { SQLDelightDriverFactory(applicationContext).createDriver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ivyWalletDI(sqlDriver) {
            setContent {
                MaterialTheme {
                    Column {
                        App()
                        HomeScreen(platform = "Android")
                    }
                }
            }
        }
    }
}

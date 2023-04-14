package ivy.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import ivy.core.App
import ivy.core.persistence.SQLDelightDriverFactory
import ivy.core.persistence.playWithDb
import ivy.home.HomeScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var people by remember {
                mutableStateOf(emptyList<String>())
            }

            LaunchedEffect(Unit) {
                people = playWithDb(SQLDelightDriverFactory(applicationContext))
            }

            MaterialTheme {
                Column {
                    App()
                    HomeScreen(platform = "Android")
                    for (person in people) {
                        Text(text = person)
                    }
                }
            }
        }
    }
}

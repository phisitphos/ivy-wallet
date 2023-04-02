package ivy.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(platform: String) {
    Text(text = "Home $platform!", color = MaterialTheme.colors.onBackground)
}

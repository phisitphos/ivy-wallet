package ivy.core.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

@Composable
fun IvyUI(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}

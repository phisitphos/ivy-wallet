package ivy.core

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ivy.core.viewmodel.DemoScreen

context(IvyWalletDI)
@Composable
fun App() {
    LazyColumn {
        item {
            DemoScreen()
        }
    }
}

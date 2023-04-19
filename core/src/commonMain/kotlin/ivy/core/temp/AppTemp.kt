package ivy.core.temp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ivy.core.IvyWalletDI
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

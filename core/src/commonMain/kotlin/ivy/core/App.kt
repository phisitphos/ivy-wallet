package ivy.core

import androidx.compose.material.Text
import androidx.compose.runtime.*
import arrow.core.raise.recover
import ivy.core.exchangerates.ExchangeProviderError
import ivy.core.exchangerates.ExchangeRatesProvider
import ivy.core.exchangerates.provider.FawazahmedExchangeRatesProvider
import ivy.core.network.createHttpClient

val httpClient = createHttpClient()
val exchangeRatesProvider: ExchangeRatesProvider = FawazahmedExchangeRatesProvider()

@Composable
fun App() {
    DemoExchangeRatesFetching(exchangeRatesProvider)
}

@Composable
private fun DemoExchangeRatesFetching(exchangeRatesProvider: ExchangeRatesProvider) {
    var rates by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        rates = recover({
            with(httpClient) {
                exchangeRatesProvider.provideLatestRates().toString()
            }
        }) { err: ExchangeProviderError ->
            "Error: $err"
        }
    }

    Text(text = rates)
}

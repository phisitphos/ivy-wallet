package ivy.core.exchangerates

import arrow.core.raise.Raise
import io.ktor.client.*
import ivy.core.exchangerates.data.ExchangeRates

interface ExchangeRatesProvider {
    context(HttpClient, Raise<ExchangeProviderError>)
    suspend fun provideLatestRates(): ExchangeRates
}

sealed interface ExchangeProviderError {
    data class IO(val errMsg: String) : ExchangeProviderError
}

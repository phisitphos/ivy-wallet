package ivy.core.exchangerates.provider

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.raise.Raise
import arrow.core.raise.catch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ivy.core.exchangerates.ExchangeProviderError
import ivy.core.exchangerates.ExchangeRatesProvider
import ivy.core.exchangerates.data.ExchangeRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

/**
 * API:
 * https://github.com/fawazahmed0/currency-api
 */
class FawazahmedExchangeRatesProvider : ExchangeRatesProvider {
    companion object {
        private val URLS = listOf(
            "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json",
            "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.min.json",
            "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/eur.min.json",
            "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/eur.json"
        )
    }

    context(HttpClient, Raise<ExchangeProviderError>)
    override suspend fun provideLatestRates(): ExchangeRates {
        TODO()
    }

    context(Raise<ExchangeProviderError>)
    private fun transformRates(response: FawazahmedResponse): ExchangeRates {
        TODO()
    }

    // region fetch from the API
    context(HttpClient)
    private suspend fun fetchRatesWithFallback(): Option<FawazahmedResponse> {
        URLS.forEach { url ->
            val response = fetchRatesFrom(url).filter {
                // empty rates are considered unsuccessful
                it.eur.isNotEmpty()
            }
            if (response is Some) return response
        }
        return None
    }

    context(HttpClient)
    private suspend fun fetchRatesFrom(url: String): Option<FawazahmedResponse> = catch({
        withContext(Dispatchers.IO) {
            val response = get(url)
            if (!response.status.isSuccess()) error("Response code not 200")
            response.body()
        }
    }) { _ ->
        None
    }

    @Serializable
    data class FawazahmedResponse(
        val date: String,
        val eur: Map<String, Double>
    )
    // endregion
}

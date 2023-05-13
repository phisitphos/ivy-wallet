package ivy.core.logic.exchangerates.provider

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.flatMap
import arrow.core.getOrElse
import arrow.core.raise.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ivy.core.data.primitives.PositiveDouble
import ivy.core.exchangerates.ExchangeProviderError
import ivy.core.exchangerates.ExchangeRatesProvider
import ivy.core.exchangerates.data.ExchangeRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * API:
 * https://github.com/fawazahmed0/currency-api
 */
class FawazahmedExchangeRatesProvider : ExchangeRatesProvider {
    companion object {
        private val URLS = setOf(
            "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json",
            "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.min.json",
            "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/eur.min.json",
            "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/eur.json"
        )
    }

    context(HttpClient, Raise<ExchangeProviderError>)
    override suspend fun provideLatestRates(): ExchangeRates =
        fetchRatesWithFallback().map(::transformResponse)
            .getOrElse { errMsg -> raise(ExchangeProviderError.IO(errMsg)) }

    private fun transformResponse(response: FawazahmedResponse): ExchangeRates {
        return ExchangeRates(
            base = ivy.core.logic.data.AssetCode("EUR"),
            rates = response.eur.mapNotNull(::transformRateEntry).toMap()
        )
    }

    private fun transformRateEntry(
        entry: Map.Entry<String, Double>
    ): Pair<ivy.core.logic.data.AssetCode, PositiveDouble>? = option {
        val (rawCurrency, rawRate) = entry
        val assetCode = ivy.core.logic.data.AssetCode.fromString(rawCurrency).bind()
        val rate = PositiveDouble.fromDouble(rawRate).bind()
        assetCode to rate
    }.getOrNull()

    // region fetch from the API
    context(HttpClient)
    private suspend fun fetchRatesWithFallback(): Either<String, FawazahmedResponse> {
        var latestFailure: Left<String> = Left("")
        URLS.forEach { url ->
            val response = fetchRatesFrom(url).flatMap {
                // empty rates are considered unsuccessful
                if (it.eur.isNotEmpty()) Right(it) else Left("Empty rates map")
            }
            when (response) {
                is Left -> latestFailure = response
                is Right -> return response
            }
        }
        return latestFailure
    }

    context(HttpClient)
    private suspend fun fetchRatesFrom(url: String): Either<String, FawazahmedResponse> = either {
        withContext(Dispatchers.IO) {
            val response = catch({ get(url) }) { e: Exception ->
                raise(e.message ?: "Unknown error")
            }
            ensure(response.status.isSuccess()) {
                "Unsuccessful response code - ${response.status.value}: ${response.body<String>()}"
            }
            response.body()
        }
    }

    @Serializable
    data class FawazahmedResponse(
        val date: String,
        val eur: Map<String, Double>
    )
    // endregion
}

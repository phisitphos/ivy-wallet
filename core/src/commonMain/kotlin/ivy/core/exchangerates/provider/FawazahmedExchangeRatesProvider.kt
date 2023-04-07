package ivy.core.exchangerates.provider

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.flatMap
import arrow.core.getOrElse
import arrow.core.raise.Raise
import arrow.core.raise.catch
import arrow.core.raise.option
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ivy.core.data.AssetCode
import ivy.core.data.primitives.PositiveDouble
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
            base = AssetCode("EUR"),
            rates = response.eur.mapNotNull(::transformRateEntry).toMap()
        )
    }

    private fun transformRateEntry(
        entry: Map.Entry<String, Double>
    ): Pair<AssetCode, PositiveDouble>? = option {
        val (rawCurrency, rawRate) = entry
        val assetCode = AssetCode.fromString(rawCurrency).bind()
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
    private suspend fun fetchRatesFrom(url: String): Either<String, FawazahmedResponse> = catch({
        withContext(Dispatchers.IO) {
            val response = get(url)
            if (!response.status.isSuccess()) {
                error("Unsuccessful response code - ${response.status.value}: ${response.body<String>()}")
            }
            Right(response.body())
        }
    }) { e: Exception ->
        Left(e.message ?: "Unknown error")
    }

    @Serializable
    data class FawazahmedResponse(
        val date: String,
        val eur: Map<String, Double>
    )
    // endregion
}

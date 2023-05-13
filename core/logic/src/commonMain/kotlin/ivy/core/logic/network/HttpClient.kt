package ivy.core.logic.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

expect val httpClientEngine: HttpClientEngine

fun createHttpClient(): HttpClient {
    return HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(contentType = ContentType.Any) // workaround for broken APIs
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10_000 // 10 seconds
        }
    }
}

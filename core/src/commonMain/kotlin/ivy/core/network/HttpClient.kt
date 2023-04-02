package ivy.core.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*


fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10_000 // 10 seconds
        }
    }
}
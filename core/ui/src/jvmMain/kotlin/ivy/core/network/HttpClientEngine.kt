package ivy.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.apache.*

actual val httpClientEngine: HttpClientEngine by lazy { Apache.create() }

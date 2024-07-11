package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val httpClient = HttpClient {

    install(Logging) {
        if (Config.PRINT_HTTP_LOGS) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTP Log: $message")
                }
            }
            level = LogLevel.ALL
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(HttpCache)
    install(WebSockets)

    defaultRequest {
        url(Config.BASE_URL)
    }
}
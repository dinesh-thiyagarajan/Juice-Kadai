package network

import dataStore.ID_TOKEN
import dataStore.REFRESH_TOKEN
import dataStore.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
}

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

    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = Settings.getString(ID_TOKEN)
                val refreshToken = Settings.getString(REFRESH_TOKEN)
                BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
            }
        }
    }

    defaultRequest {
        url(Config.BASE_URL)
        contentType(ContentType.Application.Json)
    }
}
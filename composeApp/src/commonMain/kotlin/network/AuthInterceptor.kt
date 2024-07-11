package network

import auth.repositories.AuthRepository
import dataStore.ID_TOKEN
import dataStore.REFRESH_TOKEN
import dataStore.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers

fun AuthInterceptor(httpClient: HttpClient, authRepository: AuthRepository) {

    httpClient.plugin(HttpSend).intercept { request ->
        val idToken = Settings.getString(ID_TOKEN)

        request.headers {
            append("Authorization", "Bearer $idToken")
        }
        val originalCall = execute(request)
        if (originalCall.response.status.value == 401 && idToken.isNotEmpty()) {
            val localRefreshToken = Settings.getString(REFRESH_TOKEN)
            val refreshTokenResponse = authRepository.getRefreshToken(refreshToken = localRefreshToken)

            execute(request)
        } else {
            originalCall
        }
    }

}
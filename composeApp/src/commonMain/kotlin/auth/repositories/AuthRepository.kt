package auth.repositories

import data.AuthResponse
import data.Response
import data.Status
import data.TokenResponse
import dataStore.ID_TOKEN
import dataStore.REFRESH_TOKEN
import dataStore.Settings
import dataStore.TOKEN_EXPIRES_IN
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import network.ApiConstants
import network.httpClient

class AuthRepository {

    suspend fun login(email: String, password: String): Response<AuthResponse> {
        try {
            val response = httpClient
                .post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${ApiConstants.API_KEY}") {
                    contentType(ContentType.Application.Json)
                    parameter("email", email)
                    parameter("password", password)
                    parameter("returnSecureToken", true)
                }

            println(response.bodyAsText())
            if (response.status.value == 200) {
                val authResponse = Json.decodeFromString<AuthResponse>(response.bodyAsText())
                saveAuthToDataStore(authResponse = authResponse)
                return Response(status = Status.Success, data = authResponse)
            } else {
                return Response(status = Status.Error, data = null, message = response.bodyAsText())
            }
        } catch (ex: Exception) {
            return Response(status = Status.Error, data = null, message = ex.message)
        }
    }

    private suspend fun saveAuthToDataStore(authResponse: AuthResponse) {
        Settings.addString(ID_TOKEN, authResponse.idToken)
        Settings.addString(REFRESH_TOKEN, authResponse.refreshToken)
        Settings.addInt(TOKEN_EXPIRES_IN, authResponse.expiresIn)
    }

    suspend fun isLoggedIn(): Boolean = Settings.getString(ID_TOKEN).isNotEmpty()

    suspend fun getRefreshToken(refreshToken: String?) {
        val responseBody = httpClient
            .post("https://securetoken.googleapis.com/v1/token?key=${ApiConstants.API_KEY}") {
                contentType(ContentType.Application.Json)
                parameter("grant_type", "refresh_token")
                parameter("refresh_token", refreshToken)
            }
        if (responseBody.status.value in 200..299) {
            val response = Json { ignoreUnknownKeys = true }
                .decodeFromString<TokenResponse>(responseBody.bodyAsText())

        } else {

        }
    }

}
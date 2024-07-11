package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("id_token")
    val idToken: String,
    @SerialName("email")
    val email: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("local_id")
    val localId: String
)

@Serializable
data class TokenResponse(
    @SerialName("id_token")
    val idToken: String,
    @SerialName("expires_in")
    val expiresIn: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("project_id")
    val projectId: String
)
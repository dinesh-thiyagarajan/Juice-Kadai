package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("kind")
    val kind: String,
    @SerialName("idToken")
    val idToken: String,
    @SerialName("email")
    val email: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("expiresIn")
    val expiresIn: Int,
    @SerialName("localId")
    val localId: String
)
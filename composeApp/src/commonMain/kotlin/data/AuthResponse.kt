package data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (
    val idToken : String,
    val email : String ,
    val refreshToken: String,
    val expiresIn: Int,
    val localId: String
)

@Serializable
data class TokenResponse(
    val expiresIn: String,
    val tokenType: String,
    val refreshToken: String,
    val idToken: String,
    val userId: String,
    val projectId: String
)
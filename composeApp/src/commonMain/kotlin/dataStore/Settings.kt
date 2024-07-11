package dataStore

import com.russhwolf.settings.Settings

const val ID_TOKEN = "ID_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"
const val TOKEN_EXPIRES_IN = "TOKEN_EXPIRES_IN"

object Settings {
    private var settings: Settings = Settings()

    suspend fun addString(key: String, value: String) {
        settings.putString(key = key, value = value)
    }

    suspend fun getString(key: String): String {
        return settings.getString(key = key, defaultValue = "")
    }

    suspend fun addInt(key: String, value: Int) {
        settings.putInt(key = key, value = value)
    }

}
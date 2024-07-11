package dataStore

import com.russhwolf.settings.Settings

const val ID_TOKEN = "ID_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"
const val TOKEN_EXPIRES_IN = "TOKEN_EXPIRES_IN"

object Settings {
    private var settings: Settings = Settings()

    fun addString(key: String, value: String) {
        settings.putString(key = key, value = value)
    }

    fun getString(key: String): String {
        return settings.getString(key = key, defaultValue = "")
    }

    fun addInt(key: String, value: Int) {
        settings.putInt(key = key, value = value)
    }

}
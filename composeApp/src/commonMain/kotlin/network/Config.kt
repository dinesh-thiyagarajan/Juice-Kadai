package network

import com.dineshworkspace.juicekadai.BuildKonfig

object Config {
    val PROJECT_ID = BuildKonfig.PROJECT_ID.trim('\"')
    val APP_ID = BuildKonfig.APP_ID.trim('\"')
    val API_KEY = BuildKonfig.API_KEY.trim('\"')
    val PRINT_HTTP_LOGS = BuildKonfig.PRINT_HTTP_LOGS.trim('\"').toBoolean()
}
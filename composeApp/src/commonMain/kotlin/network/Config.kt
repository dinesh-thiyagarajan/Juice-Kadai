package network

import com.dineshworkspace.juicekadai.BuildKonfig

object Config {
    val BASE_URL = BuildKonfig.BASE_URL.trim('\"')
    val PRINT_HTTP_LOGS = BuildKonfig.PRINT_HTTP_LOGS.trim('\"').toBoolean()
}
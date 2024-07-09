package network

import com.dineshworkspace.juicekadai.BuildKonfig

object ApiConstants {
    val API_KEY = BuildKonfig.FIREBASE_API_KEY
    val DATABASE_URL = BuildKonfig.FIREBASE_DATABASE_URL.trim('\"')
}
package network

import com.dineshworkspace.juicekadai.BuildKonfig

object ApiConstants {
    val DATABASE_URL = BuildKonfig.FIREBASE_DATABASE_URL.trim('\"')
}
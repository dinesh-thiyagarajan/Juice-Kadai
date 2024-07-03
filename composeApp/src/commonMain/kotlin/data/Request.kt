package data

data class Request(
    val endpoint: String,
    val queries: Map<String, Any> = emptyMap(),
    val body: Any? = null
)
package dataSource

import data.Request
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteDataSource(val okHttpClient: HttpClient) {

    suspend inline fun <reified T> getDrinksList(request: Request): Result<T> {
        return try {
            val data = okHttpClient.get(request.endpoint) {
                request.queries.forEach {
                    parameter(it.key, it.value)
                }
            }
            val result = data.body<T>()
            return Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
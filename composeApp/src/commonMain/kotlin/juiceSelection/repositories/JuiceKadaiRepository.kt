package juiceSelection.repositories

import data.Drink
import data.Response
import data.Status
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

import network.httpClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JuiceKadaiRepository {

    suspend fun getDrinksList(collection: String, skipCache: Boolean): Response<List<Drink>> {
        val drinksList: MutableList<Drink> = mutableListOf()
        try {
            val response: HttpResponse = httpClient.get("/${collection}.json") {
                contentType(ContentType.Application.Json)
                if (skipCache) {
                    header(HttpHeaders.CacheControl, "no-cache")
                }
            }

            if (response.status.value == 200) {
                val jsonElement: JsonElement = Json.parseToJsonElement(response.bodyAsText())
                val values = jsonElement.jsonObject.values.toList()
                values.forEach { drinkItem ->
                    try {
                        val drink = Drink(
                            drinkId = (drinkItem as JsonObject)["drinkId"]?.jsonPrimitive?.contentOrNull
                                ?: throw Exception("Missing drinkId"),
                            drinkName = drinkItem["drinkName"]?.jsonPrimitive?.contentOrNull
                                ?: throw Exception("Missing drinkName"),
                            drinkImage = drinkItem["drinkImage"]?.jsonPrimitive?.contentOrNull
                                ?: throw Exception("Missing drinkImage"),
                            orderCount = drinkItem["orderCount"]?.jsonPrimitive?.int
                                ?: throw Exception("Missing orderCount")
                        )
                        drinksList.add(drink)
                    } catch (e: Exception) {
                        println("Error parsing drink item: ${e.message}")
                    }
                }
                return Response(status = Status.Success, data = drinksList)
            } else {
                return Response(
                    status = Status.Error,
                    data = null,
                    message = "HTTP Error: ${response.status.value}"
                )
            }
        } catch (ex: Exception) {
            // TODO remove this try catch and handle this via interceptors
            return Response(status = Status.Error, data = null, message = ex.localizedMessage)
        }
    }

    suspend fun submitDrinksOrder(drinks: List<Drink>) {
        val drinksMap = drinks.associateBy { it.drinkId }
        val formatter = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        addDataToFirestore(formatter.format(Date()), drinksMap)
    }

    private suspend fun addDataToFirestore(collection: String, drinkData: Map<String, Drink>) {
        try {
            val response: HttpResponse = httpClient.post("/${collection}.json") {
                contentType(ContentType.Application.Json)
                setBody(drinkData)
            }

            println("Response: ${response.status}")
            println("Response body: ${response.bodyAsText()}")
        } catch (ex: Exception) {
            // handle http, socket exceptions
            // TODO remove this try catch and handle this via interceptors
        }
    }

}
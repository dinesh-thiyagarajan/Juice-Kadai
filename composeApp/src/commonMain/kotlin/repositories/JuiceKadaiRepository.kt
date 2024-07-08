package repositories

import data.Drink
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import network.ApiConstants.DATABASE_URL
import network.httpClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JuiceKadaiRepository() {

    fun getDrinksList(): List<Drink> {
        return listOf(
            Drink(1, "Orange", ""),
            Drink(2, "Lemon", ""),
            Drink(3, "Apple", ""),
            Drink(4, "Watermelon", ""),
            Drink(5, "Papaya", ""),
        )
    }

    suspend fun submitDrinksOrder(drinks: List<Drink>) {
        val drinksMap = drinks.associateBy { it.drinkId }
        val formatter = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        addDataToFirestore(formatter.format(Date()), drinksMap)
    }

    private suspend fun addDataToFirestore(collection: String, drinkData: Map<Int, Drink>) {
        val response: HttpResponse = httpClient.post("${DATABASE_URL}/${collection}.json") {
            contentType(ContentType.Application.Json)
            setBody(drinkData)
        }

        println("Response: ${response.status}")
        println("Response body: ${response.bodyAsText()}")
    }

}
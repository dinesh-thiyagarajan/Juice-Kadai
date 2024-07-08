package data

import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val drinkId: Int,
    val drinkName: String,
    val drinkImage: String,
    var itemCount: Int = 0,
    val isAvailable: Boolean = false,
    val nonAvailabilityReason: String? = null,
    val description: String? = null
)
package data

import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val drinkId: String,
    val drinkName: String,
    val drinkImage: String,
    var orderCount: Int = 0,
    val isAvailable: Boolean = false,
) {
    constructor() : this(
        drinkId = "",
        drinkName = "",
        drinkImage = "",
        orderCount = 0,
        isAvailable = false,
    )
}
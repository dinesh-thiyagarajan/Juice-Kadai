package data

import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val drinkId: String,
    val drinkName: String,
    val drinkImage: String,
    var orderCount: Int = 0,
    @field:JvmField
    val isAvailable: Boolean = false,
    val orderTimeStamp: Long = System.currentTimeMillis()
) {
    constructor() : this(
        drinkId = "",
        drinkName = "",
        drinkImage = "",
        orderCount = 0,
        isAvailable = false,
        orderTimeStamp = System.currentTimeMillis()
    )
}
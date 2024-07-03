package repositories

import data.Drink

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

}
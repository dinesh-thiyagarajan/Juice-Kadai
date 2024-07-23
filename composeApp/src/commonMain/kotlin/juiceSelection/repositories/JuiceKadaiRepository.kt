package juiceSelection.repositories

import com.google.android.gms.tasks.Tasks
import data.Drink
import data.Response
import data.Status
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import common.Config
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JuiceKadaiRepository(
    private val firebaseDatabase: FirebaseDatabase = Firebase.database,
    private val juicesCollection: String = "${Config.BASE_LOCATION}/${Config.JUICES_COLLECTION}",
    private val ordersCollection: String = "${Config.BASE_LOCATION}/${Config.ORDERS_COLLECTION}"
) {

    suspend fun getDrinksList(): Response<List<Drink>> {
        val drinksList: MutableList<Drink> = mutableListOf()
        try {
            val ref = firebaseDatabase.reference(juicesCollection)
            val dataSnapshot = Tasks.await(ref.android.get())
            for (snapshot in dataSnapshot.children) {
                val drink = snapshot.getValue(Drink::class.java)
                if (drink?.isAvailable == true) {
                    drinksList.add(drink)
                }
            }
            return Response(status = Status.Success, data = drinksList)
        } catch (ex: Exception) {
            return Response(status = Status.Error, message = ex.message)
        }
    }

    suspend fun submitDrinksOrder(drinks: List<Drink>) {
        val drinksMap = drinks.associateBy { it.drinkId }
        val formatter = SimpleDateFormat(Config.DATE_FORMAT, Locale.getDefault())
        addDrinkOrderToFirebase(formatter.format(Date()), drinksMap)
    }

    private suspend fun addDrinkOrderToFirebase(collection: String, drinkData: Map<String, Drink>) {
        try {
            // Not sure why Studio is treating this as an error
            // Read more about reified inline functions and generics
            firebaseDatabase.reference("$ordersCollection/$collection")
                .child(System.currentTimeMillis().toString())
                .setValue(drinkData) { encodeDefaults = true }
        } catch (ex: Exception) {
            // handle http, socket exceptions
            // TODO remove this try catch and handle this via interceptors
            println(ex.localizedMessage)
        }
    }

}
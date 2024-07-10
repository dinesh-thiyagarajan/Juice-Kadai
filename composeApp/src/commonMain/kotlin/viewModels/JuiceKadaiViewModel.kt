package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Drink
import data.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repositories.JuiceKadaiRepository

const val JUICE_LIST_COLLECTION = "Juices"

class JuiceKadaiViewModel(private val juiceKadaiRepository: JuiceKadaiRepository) : ViewModel() {

    /**TODO
    Handling nav with composable action directly for now
    Replace this with proper navigation and remove this from viewModel*/
    val showJuiceSelectionComposable: StateFlow<Boolean> get() = _showJuiceSelectionComposable
    private val _showJuiceSelectionComposable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val drinksUiState: StateFlow<JuicesUiState> get() = _drinksUiState
    private val _drinksUiState: MutableStateFlow<JuicesUiState> = MutableStateFlow(
        JuicesUiState.Loading
    )

    var drinksList = mutableListOf<Drink>()

    fun getDrinksList() {
        viewModelScope.launch(Dispatchers.IO) {
            val drinksResponse = juiceKadaiRepository.getDrinksList(JUICE_LIST_COLLECTION)
            when (drinksResponse.status) {
                Status.Success -> {
                    drinksList = drinksResponse.data?.toMutableList() ?: mutableListOf()
                    _drinksUiState.value = JuicesUiState.Success(drinks = drinksList)
                }

                Status.Error -> {
                    _drinksUiState.value = JuicesUiState.Error(message = drinksResponse.message)
                }
            }
        }
    }

    fun showJuiceSelectionComposable(show: Boolean) {
        _showJuiceSelectionComposable.value = show
    }

    // TODO optimize this logic further, instead of creating a new mutable list everytime
    fun onCounterChanged(drinkId: String, count: Int) {
        if (count < 0) return
        val index = drinksList.indexOfFirst { it.drinkId == drinkId }
        val drink = drinksList[index]
        val updatedDrink = drink.copy(orderCount = count)
        val updatedList = drinksList.toMutableList()
        updatedList[index] = updatedDrink
        drinksList = updatedList
        _drinksUiState.value = JuicesUiState.Success(drinks = drinksList)
    }

    fun onSubmit() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedDrinks = drinksList.filter { it.orderCount > 0 }
            if (selectedDrinks.isEmpty()) {
                _showJuiceSelectionComposable.value = false
                return@launch
            }
            juiceKadaiRepository.submitDrinksOrder(drinks = selectedDrinks)
            _showJuiceSelectionComposable.value = false
        }
    }
}

sealed interface JuicesUiState {
    data object Loading : JuicesUiState
    data class Success(val drinks: List<Drink>) : JuicesUiState
    data class Error(val message: String?) : JuicesUiState
}
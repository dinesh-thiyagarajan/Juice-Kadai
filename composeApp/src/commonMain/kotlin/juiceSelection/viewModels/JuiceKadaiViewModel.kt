package juiceSelection.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Drink
import data.SnackUiState
import data.Status
import dataStore.ID_TOKEN
import dataStore.Settings
import juiceSelection.repositories.JuiceKadaiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    val snackUiState: StateFlow<SnackUiState> get() = _snackUiState
    private val _snackUiState: MutableStateFlow<SnackUiState> = MutableStateFlow(
        SnackUiState()
    )

    var drinksList = mutableListOf<Drink>()

    fun refreshDrinksList() {
        getDrinksList(skipCache = true)
    }

    fun getDrinksList(skipCache: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val drinksResponse = juiceKadaiRepository.getDrinksList(
                JUICE_LIST_COLLECTION,
                skipCache = skipCache,
            )
            when (drinksResponse.status) {
                Status.Success -> {
                    drinksList = drinksResponse.data?.toMutableList() ?: mutableListOf()
                    _drinksUiState.value = JuicesUiState.Success(drinks = drinksList)
                    if (skipCache) {
                        val updatedSnackUiState = _snackUiState.value.copy(
                            showSnackBar = true,
                            snackMessage = "Updated Juice List is Available now"
                        )
                        _snackUiState.value = updatedSnackUiState
                    }
                }

                Status.Error -> {
                    _drinksUiState.value = JuicesUiState.Error(message = drinksResponse.message)
                    if (skipCache) {
                        val updatedSnackUiState = _snackUiState.value.copy(
                            showSnackBar = true,
                            snackMessage = "Updating Juices list failed, Please check with Admin"
                        )
                        _snackUiState.value = updatedSnackUiState
                    }
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

    fun updateSnackBarUiState(show: Boolean = false, message: String? = null) {
        val updatedSnackUiState =
            _snackUiState.value.copy(showSnackBar = show, snackMessage = message)
        _snackUiState.value = updatedSnackUiState
    }
}

sealed interface JuicesUiState {
    data object Loading : JuicesUiState
    data class Success(val drinks: List<Drink>) : JuicesUiState
    data class Error(val message: String?) : JuicesUiState
}
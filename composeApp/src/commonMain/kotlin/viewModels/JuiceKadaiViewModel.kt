package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repositories.JuiceKadaiRepository

class JuiceKadaiViewModel(private val juiceKadaiRepository: JuiceKadaiRepository) : ViewModel() {

    /**TODO
    Handling nav with composable action directly for now
    Replace this with proper navigation and remove this from viewModel*/
    val showJuiceSelectionComposable: StateFlow<Boolean> get() = _showJuiceSelectionComposable
    private val _showJuiceSelectionComposable: MutableStateFlow<Boolean> = MutableStateFlow(false)


    val drinks: StateFlow<List<Drink>> get() = _drinks
    private val _drinks: MutableStateFlow<List<Drink>> = MutableStateFlow(
        listOf()
    )

    fun getDrinksList() {
        viewModelScope.launch {
            juiceKadaiRepository.getDrinksList().let {
                _drinks.value = it
            }
        }
    }

    fun showJuiceSelectionComposable(show: Boolean) {
        _showJuiceSelectionComposable.value = show
    }

    // TODO optimize this logic further, instead of creating a new mutable list everytime
    fun onCounterChanged(drinkId: Int, count: Int) {
        val index = drinks.value.indexOfFirst { it.drinkId == drinkId }
        val drink = _drinks.value[index]
        val updatedDrink = drink.copy(itemCount = count)
        val updatedList = _drinks.value.toMutableList()
        updatedList[index] = updatedDrink
        _drinks.value = updatedList
    }

    fun onSubmit() {
        viewModelScope.launch {
            juiceKadaiRepository.submitDrinksOrder(drinks = drinks.value)
            _showJuiceSelectionComposable.value = false
        }
    }

}
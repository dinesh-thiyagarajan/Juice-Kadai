package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repositories.JuiceKadaiRepository

class JuiceKadaiViewModel(private val juiceKadaiRepository: JuiceKadaiRepository) : ViewModel() {

    val showJuiceSelectionComposable: StateFlow<Boolean> get() = _showJuiceSelectionComposable
    private val _showJuiceSelectionComposable: MutableStateFlow<Boolean> = MutableStateFlow(false)


    val drinks: StateFlow<List<Drink>> get() = _drinks
    private val _drinks: MutableStateFlow<List<Drink>> = MutableStateFlow(
        listOf()
    )

    suspend fun getDrinksList() {
        viewModelScope.launch {
            juiceKadaiRepository.getDrinksList().let {
                _drinks.value = it
            }
        }
    }

    fun showJuiceSelectionComposable(show: Boolean) {
        _showJuiceSelectionComposable.value = show
    }

}
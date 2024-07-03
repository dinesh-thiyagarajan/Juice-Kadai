import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JuiceKadaiViewModel() : ViewModel() {

    suspend fun getJuiceList() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}
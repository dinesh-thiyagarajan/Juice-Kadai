import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import auth.repositories.AuthRepository
import auth.viewModels.AuthViewModel
import juiceSelection.repositories.JuiceKadaiRepository
import juiceSelection.viewModels.JuiceKadaiViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Juice Kadai",
    ) {
        JuiceKadaiApp(
            AuthViewModel(AuthRepository()),
            JuiceKadaiViewModel(JuiceKadaiRepository())
        )
    }
}
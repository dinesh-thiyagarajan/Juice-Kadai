import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import repositories.JuiceKadaiRepository
import viewModels.JuiceKadaiViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Juice Kadai",
    ) {
        JuiceKadaiApp(JuiceKadaiViewModel(JuiceKadaiRepository()))
    }
}
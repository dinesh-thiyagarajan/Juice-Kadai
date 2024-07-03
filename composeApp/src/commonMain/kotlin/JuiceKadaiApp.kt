import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import composables.DrinkSelectionComposable
import composables.HomeComposable
import org.jetbrains.compose.ui.tooling.preview.Preview
import viewModels.JuiceKadaiViewModel

@Composable
@Preview
fun JuiceKadaiApp(juiceKadaiViewModel: JuiceKadaiViewModel) {
    val showJuiceSelectionComposable =
        juiceKadaiViewModel.showJuiceSelectionComposable.collectAsState()
    MaterialTheme {
        if (showJuiceSelectionComposable.value) {
            DrinkSelectionComposable(juiceKadaiViewModel)
        } else {
            HomeComposable(juiceKadaiViewModel)
        }
    }
}
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import composables.HomeComposable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun JuiceKadaiApp(juiceKadaiViewModel: JuiceKadaiViewModel) {
    MaterialTheme {
        HomeComposable(juiceKadaiViewModel)
    }
}
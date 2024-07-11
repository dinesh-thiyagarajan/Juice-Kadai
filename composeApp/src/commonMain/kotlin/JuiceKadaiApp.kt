import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import auth.composables.FetchingLoginStatusComposable
import auth.composables.LoginComposable
import auth.viewModels.AuthUiState
import auth.viewModels.AuthViewModel
import juiceSelection.composables.DrinkSelectionComposable
import juiceSelection.composables.HomeComposable
import juiceSelection.viewModels.JuiceKadaiViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun JuiceKadaiApp(authViewModel: AuthViewModel, juiceKadaiViewModel: JuiceKadaiViewModel) {
    val showJuiceSelectionComposable =
        juiceKadaiViewModel.showJuiceSelectionComposable.collectAsState()
    val authUiState = authViewModel.authUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        when (authUiState.value) {
            AuthUiState.FetchingLoginStatus -> {
                FetchingLoginStatusComposable()
            }

            AuthUiState.NotLoggedIn -> {
                LoginComposable(authViewModel = authViewModel, coroutineScope = coroutineScope)
            }

            AuthUiState.LoggedIn -> {
                if (showJuiceSelectionComposable.value) {
                    DrinkSelectionComposable(juiceKadaiViewModel)
                } else {
                    HomeComposable(juiceKadaiViewModel)
                }
            }

            AuthUiState.LoginInProgress -> {
                // show loader
            }

            AuthUiState.Error -> {
                // show error
            }
        }
    }
}
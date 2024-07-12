import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import auth.composables.FetchingLoginStatusComposable
import auth.composables.LoginComposable
import auth.viewModels.AuthUiState
import auth.viewModels.AuthViewModel
import juiceSelection.composables.DrinkSelectionComposable
import juiceSelection.composables.HomeComposable
import juiceSelection.viewModels.JuiceKadaiViewModel
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_404
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Logging You In")
                }
            }

            AuthUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_404),
                        contentDescription = "error",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Please contact admin reg login access",
                        modifier = Modifier.padding(30.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.wrapContentWidth()
                            .padding(start = 10.dp),
                        onClick = {
                            coroutineScope.launch {
                                authViewModel.updateAuthUiState(AuthUiState.NotLoggedIn)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Go to Login", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
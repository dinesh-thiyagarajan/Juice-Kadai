package juiceSelection.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.theme.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_apple
import juicekadai.composeapp.generated.resources.ic_coffee
import juicekadai.composeapp.generated.resources.ic_fruit_bowl
import juicekadai.composeapp.generated.resources.ic_orange
import juicekadai.composeapp.generated.resources.ic_refresh
import juicekadai.composeapp.generated.resources.ic_tea
import juicekadai.composeapp.generated.resources.ic_watermelon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import juiceSelection.viewModels.JuiceKadaiViewModel


@Composable
fun ImageSwitcher() {
    val images = listOf(
        Res.drawable.ic_orange,
        Res.drawable.ic_apple,
        Res.drawable.ic_fruit_bowl,
        Res.drawable.ic_tea,
        Res.drawable.ic_coffee,
        Res.drawable.ic_watermelon
    )
    var currentImage by remember { mutableStateOf(0) }

    LaunchedEffect(currentImage) {
        while (true) {
            delay(1500)
            currentImage = (currentImage + 1) % images.size
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier.size(100.dp)
        ) {
            AnimatedContent(
                targetState = currentImage,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                        animationSpec = tween(
                            300
                        )
                    )
                }
            ) { targetImage ->
                Image(
                    painter = painterResource(resource = images[targetImage]),
                    contentDescription = "juice images",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun HomeComposable(juiceKadaiViewModel: JuiceKadaiViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val snackUiState by juiceKadaiViewModel.snackUiState.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackBarHostState = scaffoldState.snackbarHostState

    LaunchedEffect(snackUiState) {
        snackUiState.let {
            it.snackMessage?.let { msg ->
                val result = snackBarHostState.showSnackbar(
                    message = msg,
                    actionLabel = "Dismiss"
                )
                if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                    juiceKadaiViewModel.updateSnackBarUiState(show = false)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            var userId by remember { mutableStateOf("") }

            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_refresh),
                    contentDescription = "refresh juice orders",
                    modifier = Modifier.size(30.dp).clickable {
                        coroutineScope.launch {
                            juiceKadaiViewModel.refreshDrinksList()
                        }
                    },
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageSwitcher()
                Spacer(modifier = Modifier.padding(top = 20.dp))
                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("Please enter your ID") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primary_wave_blue,
                        focusedLabelColor = primary_wave_blue,
                        unfocusedBorderColor = primary_grey,
                        unfocusedLabelColor = primary_black
                    ),
                    modifier = Modifier.fillMaxWidth(0.5f).focusable(enabled = true)
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                Button(
                    onClick = {
                        juiceKadaiViewModel.showJuiceSelectionComposable(show = true)
                    },
                    enabled = userId.isNotEmpty(),
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = primary_grey,
                        backgroundColor = primary_wave_blue,
                        contentColor = primary_white
                    )
                ) {
                    Text("Submit")
                }
            }
        }
    }

}
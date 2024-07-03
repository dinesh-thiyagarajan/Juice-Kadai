import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_juice_icon
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeComposable() {
    Box(modifier = Modifier.fillMaxSize()) {
        var twId by remember { mutableStateOf("") }
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
        }

        val offsetY by animateFloatAsState(
            targetValue = if (visible) 0f else -1000f,
            animationSpec = tween(durationMillis = 1000)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.ic_juice_icon),
                contentDescription = "Juice Image",
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = offsetY.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            OutlinedTextField(
                value = twId,
                isError = twId.isEmpty(),
                onValueChange = { twId = it },
                label = { Text("Please enter your ID") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Button(
                onClick = {

                },
                enabled = twId.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(0.2f)
            ) {
                Text("Submit")
            }

        }
    }

}
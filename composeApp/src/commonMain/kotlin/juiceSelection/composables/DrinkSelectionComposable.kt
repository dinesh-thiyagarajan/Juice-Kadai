package juiceSelection.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.removeSpacesAndLowerCase
import common.theme.primary_wave_blue
import data.Drink
import juiceSelection.viewModels.JuiceKadaiViewModel
import juiceSelection.viewModels.JuicesUiState
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_404
import juicekadai.composeapp.generated.resources.ic_apple
import juicekadai.composeapp.generated.resources.ic_banana
import juicekadai.composeapp.generated.resources.ic_coffee
import juicekadai.composeapp.generated.resources.ic_fruit_bowl
import juicekadai.composeapp.generated.resources.ic_generic_juice
import juicekadai.composeapp.generated.resources.ic_lemon
import juicekadai.composeapp.generated.resources.ic_orange
import juicekadai.composeapp.generated.resources.ic_tea
import juicekadai.composeapp.generated.resources.ic_watermelon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DrinkSelectionComposable(juiceKadaiViewModel: JuiceKadaiViewModel) {
    LaunchedEffect(juiceKadaiViewModel) {
        juiceKadaiViewModel.getDrinksList()
    }
    val drinksUiState = juiceKadaiViewModel.drinksUiState.collectAsState()
    when (drinksUiState.value) {
        is JuicesUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = primary_wave_blue)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Please wait while we fetch the juices list for you")
            }
        }

        is JuicesUiState.Success -> {
            val drinks = (drinksUiState.value as JuicesUiState.Success).drinks
            GridListWithRoundedCardViews(drinks, juiceKadaiViewModel = juiceKadaiViewModel)
        }

        is JuicesUiState.Error -> {
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
                    text = "Something went wrong while fetching the data, Please check your internet connection, if its fine please contact Admin",
                    modifier = Modifier.padding(30.dp),
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun GridListWithRoundedCardViews(
    drinks: List<Drink>,
    juiceKadaiViewModel: JuiceKadaiViewModel
) {
    Scaffold {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { juiceKadaiViewModel.onSubmit() },
                    backgroundColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Done"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(drinks.size) { index ->
                            RoundedCardView(
                                drinkId = drinks[index].drinkId,
                                drinkName = drinks[index].drinkName,
                                juiceKadaiViewModel = juiceKadaiViewModel
                            )
                        }
                    }
                }
            }
        )
    }
}


fun getResourceDrawable(drinkName: String): DrawableResource {
    return when (drinkName.removeSpacesAndLowerCase()) {
        "orange" -> Res.drawable.ic_orange
        "apple" -> Res.drawable.ic_apple
        "banana" -> Res.drawable.ic_banana
        "lemon" -> Res.drawable.ic_lemon
        "watermelon" -> Res.drawable.ic_watermelon
        "fruitbowl" -> Res.drawable.ic_fruit_bowl
        "tea" -> Res.drawable.ic_tea
        "coffee" -> Res.drawable.ic_coffee
        else -> Res.drawable.ic_generic_juice
    }
}

@Composable
fun RoundedCardView(
    drinkId: String,
    drinkName: String,
    juiceKadaiViewModel: JuiceKadaiViewModel
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {
            Image(
                painter = painterResource(getResourceDrawable(drinkName)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                contentScale = ContentScale.Fit
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
            ) {
                Text(
                    text = drinkName,
                    style = MaterialTheme.typography.h4,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Counter(drinkId = drinkId, juiceKadaiViewModel = juiceKadaiViewModel)
        }
    }
}

@Composable
fun Counter(drinkId: String, juiceKadaiViewModel: JuiceKadaiViewModel) {
    val orderCount = juiceKadaiViewModel.drinksList.find { it.drinkId == drinkId }?.orderCount ?: 0
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.wrapContentWidth().fillMaxHeight().weight(1f)
                .padding(start = 10.dp),
            onClick = {
                juiceKadaiViewModel.onCounterChanged(
                    drinkId = drinkId,
                    count = orderCount - 1
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.White
            ),
        ) {
            Text(text = "-", textAlign = TextAlign.Center)
        }

        Text(
            text = orderCount.toString(),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.wrapContentWidth().weight(1f).align(Alignment.CenterVertically)
        )

        Button(
            modifier = Modifier.wrapContentWidth().weight(1f).padding(end = 10.dp),
            onClick = {
                juiceKadaiViewModel.onCounterChanged(
                    drinkId = drinkId,
                    count = orderCount + 1
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.White
            ),
        ) {
            Text(text = "+", textAlign = TextAlign.Center)
        }
    }
}
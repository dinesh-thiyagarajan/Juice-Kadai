package composables

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Drink
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_juice
import org.jetbrains.compose.resources.painterResource
import viewModels.JuiceKadaiViewModel
import viewModels.JuicesUiState

@Composable
fun DrinkSelectionComposable(juiceKadaiViewModel: JuiceKadaiViewModel) {
    LaunchedEffect(juiceKadaiViewModel) {
        juiceKadaiViewModel.getDrinksList()
    }
    val drinksUiState = juiceKadaiViewModel.drinksUiState.collectAsState()
    when (drinksUiState.value) {
        is JuicesUiState.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(10.dp))
                Text("Please wait while we fetch the juices list for you")
            }
        }

        is JuicesUiState.Success -> {
            val drinks = (drinksUiState.value as JuicesUiState.Success).drinks
            GridListWithRoundedCardViews(drinks, juiceKadaiViewModel = juiceKadaiViewModel)
        }

        is JuicesUiState.Error -> {}
    }
}

@Composable
fun GridListWithRoundedCardViews(
    drinks: List<Drink>,
    numColumns: Int = 2,
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
                        contentDescription = "Add"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(numColumns),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(drinks.size) { index ->
                            RoundedCardView(
                                drinkId = drinks[index].drinkId,
                                title = drinks[index].drinkName,
                                juiceKadaiViewModel = juiceKadaiViewModel
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun RoundedCardView(drinkId: String, title: String, juiceKadaiViewModel: JuiceKadaiViewModel) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {
            Image(
                painter = painterResource(Res.drawable.ic_juice),
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
                    text = title,
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
        Button(onClick = {
            juiceKadaiViewModel.onCounterChanged(
                drinkId = drinkId,
                count = orderCount - 1
            )
        }) {
            Text(text = "-")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = orderCount.toString(), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            juiceKadaiViewModel.onCounterChanged(

                drinkId = drinkId,
                count = orderCount + 1
            )
        }) {
            Text(text = "+")
        }
    }
}
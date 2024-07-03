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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Drink
import juicekadai.composeapp.generated.resources.Res
import juicekadai.composeapp.generated.resources.ic_juice_icon
import org.jetbrains.compose.resources.painterResource
import viewModels.JuiceKadaiViewModel

@Composable
fun DrinkSelectionComposable(juiceKadaiViewModel: JuiceKadaiViewModel) {
    LaunchedEffect(juiceKadaiViewModel) {
        juiceKadaiViewModel.getDrinksList()
    }
    val drinks = juiceKadaiViewModel.drinks.collectAsState()
    GridListWithRoundedCardViews(drinks.value, juiceKadaiViewModel = juiceKadaiViewModel)
}

@Composable
fun GridListWithRoundedCardViews(
    drinks: List<Drink>,
    numColumns: Int = 2,
    juiceKadaiViewModel: JuiceKadaiViewModel
) {
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

@Composable
fun RoundedCardView(drinkId: Int, title: String, juiceKadaiViewModel: JuiceKadaiViewModel) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {
            Image(
                painter = painterResource(Res.drawable.ic_juice_icon),
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
fun Counter(drinkId: Int, juiceKadaiViewModel: JuiceKadaiViewModel) {
    val count =
        juiceKadaiViewModel.drinks.collectAsState().value.find { it.drinkId == drinkId }?.itemCount
            ?: 0
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            juiceKadaiViewModel.onCounterChanged(
                drinkId = drinkId,
                count = count - 1
            )
        }) {
            Text(text = "-")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = count.toString(), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            juiceKadaiViewModel.onCounterChanged(
                drinkId = drinkId,
                count = count + 1
            )
        }) {
            Text(text = "+")
        }
    }
}
package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
    GridListWithRoundedCardViews(drinks.value)
}

@Composable
fun GridListWithRoundedCardViews(
    drinks: List<Drink>,
    numColumns: Int = 2
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(numColumns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(drinks.size) { index ->
            RoundedCardView(
                imageResId = 1,
                title = drinks[index].drinkName,
                description = "",
            )
        }
    }
}

@Composable
fun RoundedCardView(imageResId: Int, title: String, description: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.ic_juice_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
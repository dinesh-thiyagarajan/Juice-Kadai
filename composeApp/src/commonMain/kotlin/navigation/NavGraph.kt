package navigation

import androidx.compose.runtime.Composable

private object Route {
    const val HOME_SCREEN = "home"
    const val JUICE_SELECTION_SCREEN = "juice_selection"
}

sealed class Router(val route: String) {
    data object HomeRouter : Router(route = Route.HOME_SCREEN)
    data object JuiceSelectionRouter : Router(route = Route.JUICE_SELECTION_SCREEN)
}
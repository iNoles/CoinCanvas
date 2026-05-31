package org.jonathansteele.coincanvas

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun SinglePaneNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            CoinHomeScreen(
                onCoinClick = { coin ->
                    navController.navigate(DetailRoute(coin.id))
                }
            )
        }
        composable<DetailRoute> {
            val args: DetailRoute = it.toRoute()
            CoinDetailRoute(
                coinId = args.coinId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

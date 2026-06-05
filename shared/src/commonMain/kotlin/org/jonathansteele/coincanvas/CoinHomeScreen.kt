package org.jonathansteele.coincanvas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jonathansteele.coincanvas.components.CoinCanvasTopBarExpressive
import org.jonathansteele.coincanvas.components.CoinCardExpressive
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.data.model.FavoritesSet
import org.jonathansteele.coincanvas.viewmodel.CryptoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinHomeScreen(
    onCoinClick: (Coin) -> Unit
) {
    val viewModel: CryptoViewModel = koinViewModel()

    val coins by viewModel.coins.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    var selectedTab by remember { mutableStateOf(HomeTab.COINS) }

    Scaffold(
        topBar = {
            CoinCanvasTopBarExpressive(
                currentSort = sortType,
                onSortSelected = { viewModel.sort(it) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            PrimaryTabRow(selectedTab.ordinal) {
                Tab(
                    selected = selectedTab == HomeTab.COINS,
                    onClick = { selectedTab = HomeTab.COINS },
                    text = { Text("Coins") }
                )
                Tab(
                    selected = selectedTab == HomeTab.FAVORITES,
                    onClick = { selectedTab = HomeTab.FAVORITES },
                    text = { Text("Favorites") }
                )
            }

            Spacer(Modifier.height(16.dp))

            when (selectedTab) {
                HomeTab.COINS -> CoinList(
                    coins = coins,
                    favorites = favorites,
                    onCoinClick = onCoinClick,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )

                HomeTab.FAVORITES -> FavoriteList(
                    coins = coins.filter { favorites.contains(it.id) },
                    favorites = favorites,
                    onCoinClick = onCoinClick,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )
            }
        }
    }
}

@Composable
fun CoinList(
    coins: List<Coin>,
    favorites: FavoritesSet,
    onCoinClick: (Coin) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(coins, { it.id }) { coin ->
            ExpressiveListCard {
                CoinCardExpressive(
                    coin = coin,
                    isFavorite = favorites.contains(coin.id),
                    onFavoriteClick = { onFavoriteClick(coin.id) },
                    onClick = { onCoinClick(coin) }
                )
            }
        }
    }
}

@Composable
fun FavoriteList(
    coins: List<Coin>,
    favorites: FavoritesSet,
    onCoinClick: (Coin) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    if (coins.isEmpty()) {
        Text(
            "No favorites yet",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(20.dp)
        )
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(coins, { it.id }) { coin ->
            ExpressiveListCard {
                CoinCardExpressive(
                    coin = coin,
                    isFavorite = favorites.contains(coin.id),
                    onFavoriteClick = { onFavoriteClick(coin.id) },
                    onClick = { onCoinClick(coin) }
                )
            }
        }
    }
}

@Composable
fun ExpressiveListCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            content()
        }
    }
}

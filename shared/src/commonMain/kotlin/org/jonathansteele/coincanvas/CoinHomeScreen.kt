package org.jonathansteele.coincanvas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    // Local UI state
    var selectedTab by remember { mutableStateOf(HomeTab.COINS) }
    var sortType by remember { mutableStateOf(SortType.MARKET_CAP) }

    // Sorting applied in UI
    val sortedCoins = remember(coins, sortType) {
        sortCoins(coins, sortType)
    }

    // Pull-to-refresh state
    var refreshing by remember { mutableStateOf(false) }
    val pullState = rememberPullToRefreshState()

    // End refresh when new coins arrive
    LaunchedEffect(coins) {
        if (refreshing) refreshing = false
    }

    PullToRefreshBox(
        isRefreshing = refreshing,
        onRefresh = {
            refreshing = true
            viewModel.loadCoins()
        },
        state = pullState
    ) {

        Scaffold(
            topBar = {
                CoinCanvasTopBarExpressive(
                    currentSort = sortType,
                    onSortSelected = { sortType = it }
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
                        coins = sortedCoins,
                        favorites = favorites,
                        onCoinClick = onCoinClick,
                        onFavoriteClick = { viewModel.toggleFavorite(it) }
                    )

                    HomeTab.FAVORITES -> FavoriteList(
                        coins = sortedCoins.filter { favorites.contains(it.id) },
                        favorites = favorites,
                        onCoinClick = onCoinClick,
                        onFavoriteClick = { viewModel.toggleFavorite(it) }
                    )
                }
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

fun sortCoins(list: List<Coin>, sortType: SortType): List<Coin> =
    when (sortType) {
        SortType.PRICE -> list.sortedByDescending { it.currentPrice }
        SortType.CHANGE -> list.sortedByDescending { it.priceChangeWith24h ?: 0.0 }
        SortType.VOLUME -> list.sortedByDescending { it.totalVolume ?: 0.0 }
        SortType.MARKET_CAP -> list.sortedByDescending { it.marketCap ?: 0.0 }
    }

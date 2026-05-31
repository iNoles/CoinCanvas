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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jonathansteele.coincanvas.components.CoinCanvasTopBarExpressive
import org.jonathansteele.coincanvas.components.CoinCardExpressive
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.viewmodel.CryptoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinHomeScreen(
    onCoinClick: (Coin) -> Unit
) {
    val viewModel: CryptoViewModel = koinViewModel()
    val coins by viewModel.coins.collectAsState()
    val sortType by viewModel.sortType.collectAsState()

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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            SectionHeader(
                title = "Top Cryptocurrencies",
                color = MaterialTheme.colorScheme.primary
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(coins) { coin ->
                    ExpressiveListCard {
                        CoinCardExpressive(
                            coin = coin,
                            onClick = { onCoinClick(coin) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = color
    )
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

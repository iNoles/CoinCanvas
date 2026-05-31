package org.jonathansteele.coincanvas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jonathansteele.coincanvas.viewmodel.CoinDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CoinDetailRoute(
    coinId: String,
    onBack: () -> Unit
) {
    val viewModel: CoinDetailViewModel = koinViewModel(
        parameters = { parametersOf(coinId) }
    )

    val state by viewModel.state.collectAsState()

    CoinDetailScreen(
        state = state,
        onBack = onBack
    )
}
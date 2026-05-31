package org.jonathansteele.coincanvas

import androidx.compose.runtime.Stable
import org.jonathansteele.coincanvas.data.model.Coin

@Stable
data class CoinDetailState(
    val coin: Coin? = null,
    val history: List<Double> = emptyList(),
    val isLoading: Boolean = true
)
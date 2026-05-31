package org.jonathansteele.coincanvas.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponse(
    val prices: List<List<Double>>
)

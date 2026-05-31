package org.jonathansteele.coincanvas.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    @JsonNames("current_price")
    val currentPrice: Double,
    @JsonNames("market_cap")
    val marketCap: Double?,
    @JsonNames("total_volume")
    val totalVolume: Double?,
    @JsonNames("price_change_percentage_24h")
    val priceChangeWith24h: Double?,
    @JsonNames("circulating_supply")
    val circulatingSupply: Double?,
    @JsonNames("total_supply")
    val totalSupply: Double?
)

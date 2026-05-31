package org.jonathansteele.coincanvas.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.data.model.CoinHistoryResponse

class CryptoApi(private val client: HttpClient) {

    suspend fun fetchCoins(): List<Coin> {
        return client.get("https://api.coingecko.com/api/v3/coins/markets") {
            parameter("vs_currency", "usd")
            parameter("order", "market_cap_desc")
            parameter("per_page", 50)
            parameter("page", 1)
            parameter("sparkline", true)
        }.body()
    }

    suspend fun fetchCoinHistory(id: String): List<Double> {
        val response: CoinHistoryResponse = client.get(
            "https://api.coingecko.com/api/v3/coins/$id/market_chart"
        ) {
            parameter("vs_currency", "usd")
            parameter("days", 1)
        }.body()

        return response.prices.map { it[1] }
    }
}

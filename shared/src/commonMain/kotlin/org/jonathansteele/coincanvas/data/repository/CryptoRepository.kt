package org.jonathansteele.coincanvas.data.repository

import kotlin.time.Clock
import org.jonathansteele.coincanvas.data.api.CryptoApi
import org.jonathansteele.coincanvas.data.model.Coin

class CryptoRepository(
    private val api: CryptoApi
) {
    private var cachedCoins: List<Coin> = emptyList()
    private val historyCache = mutableMapOf<String, Pair<Long, List<Double>>>()
    private var lastCoinsFetchTime: Long = 0
    private val coinsTTL = 5 * 60 * 1000 // 5 minutes

    suspend fun fetchCoins(): List<Coin> {
        val now = Clock.System.now().toEpochMilliseconds()

        if (cachedCoins.isNotEmpty() && now - lastCoinsFetchTime < coinsTTL) {
            return cachedCoins
        }

        val fresh = api.fetchCoins()
        cachedCoins = fresh
        lastCoinsFetchTime = now
        return fresh
    }

    fun getCoinById(id: String): Coin? {
        return cachedCoins.firstOrNull { it.id == id }
    }

    suspend fun getHistoryForCoin(id: String): List<Double> {
        val now = Clock.System.now().toEpochMilliseconds()
        val ttl = 10 * 60 * 1000 // 10 minutes

        historyCache[id]?.let { (timestamp, data) ->
            if (now - timestamp < ttl) return data
        }

        val fresh = api.fetchCoinHistory(id)
        historyCache[id] = now to fresh
        return fresh
    }
}

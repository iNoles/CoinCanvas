package org.jonathansteele.coincanvas.data.repository

import kotlin.time.Clock
import org.jonathansteele.coincanvas.CoinsQueries
import org.jonathansteele.coincanvas.FavoritesQueries
import org.jonathansteele.coincanvas.data.api.CryptoApi
import org.jonathansteele.coincanvas.data.model.Coin

class CryptoRepository(
    private val api: CryptoApi,
    private val coinsQueries: CoinsQueries,
    private val favoritesQueries: FavoritesQueries
) {
    private var cachedCoins: List<Coin> = emptyList()
    private var lastCoinsFetchTime: Long = 0
    private val coinsTTL = 5 * 60 * 1000 // 5 minutes

    suspend fun fetchCoins(): List<Coin> {
        val now = Clock.System.now().toEpochMilliseconds()

        // 1. Memory cache
        if (cachedCoins.isNotEmpty() && now - lastCoinsFetchTime < coinsTTL) {
            return cachedCoins
        }

        // 2. SQLDelight cache
        val local = coinsQueries.selectAll().executeAsList()
        if (local.isNotEmpty()) {
            cachedCoins = local.map { row ->
                Coin(
                    id = row.id,
                    name = row.name,
                    symbol = row.symbol,
                    image = row.image,
                    currentPrice = row.current_price,
                    marketCap = row.market_cap,
                    totalVolume = row.total_volume,
                    priceChangeWith24h = row.price_change_24h,
                    circulatingSupply = row.circulating_supply,
                    totalSupply = row.total_supply
                )
            }
            return cachedCoins
        }

        // 3. API fetch
        val remote = api.fetchCoins()

        // 4. Save to SQLDelight
        coinsQueries.transaction {
            coinsQueries.clear()
            remote.forEach { coin ->
                coinsQueries.insert(
                    id = coin.id,
                    name = coin.name,
                    symbol = coin.symbol,
                    image = coin.image,
                    current_price = coin.currentPrice,
                    market_cap = coin.marketCap,
                    total_volume = coin.totalVolume,
                    price_change_24h = coin.priceChangeWith24h,
                    circulating_supply = coin.circulatingSupply,
                    total_supply = coin.totalSupply
                )
            }
        }

        // 5. Save to memory
        cachedCoins = remote
        lastCoinsFetchTime = now

        return remote
    }

    fun getCoinById(id: String): Coin? {
        // Memory first
        cachedCoins.firstOrNull { it.id == id }?.let { return it }

        // SQLDelight fallback
        val row = coinsQueries.selectById(id).executeAsOneOrNull() ?: return null

        return Coin(
            id = row.id,
            name = row.name,
            symbol = row.symbol,
            image = row.image,
            currentPrice = row.current_price,
            marketCap = row.market_cap,
            totalVolume = row.total_volume,
            priceChangeWith24h = row.price_change_24h,
            circulatingSupply = row.circulating_supply,
            totalSupply = row.total_supply
        )
    }

    fun getFavorites(): List<String> =
        favoritesQueries.selectAll().executeAsList().map { it }
    fun isFavorite(id: String): Boolean =
        favoritesQueries.isFavorite(id).executeAsOne()
    fun addFavorite(id: String) {
        favoritesQueries.insert(id)
    }
    fun removeFavorite(id: String) {
        favoritesQueries.delete(id)
    }
    fun toggleFavorite(id: String) {
        if (isFavorite(id)) removeFavorite(id)
        else addFavorite(id)
    }

    // History stays in memory for now
    private val historyCache = mutableMapOf<String, Pair<Long, List<Double>>>()

    suspend fun getHistoryForCoin(id: String): List<Double> {
        val now = Clock.System.now().toEpochMilliseconds()
        val ttl = 10 * 60 * 1000

        historyCache[id]?.let { (timestamp, data) ->
            if (now - timestamp < ttl) return data
        }

        val fresh = api.fetchCoinHistory(id)
        historyCache[id] = now to fresh
        return fresh
    }
}

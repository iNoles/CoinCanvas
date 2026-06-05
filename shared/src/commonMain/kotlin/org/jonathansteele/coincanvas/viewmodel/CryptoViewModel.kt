package org.jonathansteele.coincanvas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.jonathansteele.coincanvas.SortType
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.data.model.FavoritesSet
import org.jonathansteele.coincanvas.data.repository.CryptoRepository

class CryptoViewModel(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _coins = MutableStateFlow<List<Coin>>(emptyList())
    val coins = _coins.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.MARKET_CAP)
    val sortType = _sortType.asStateFlow()

    private val _favorites = MutableStateFlow(FavoritesSet(emptySet()))
    val favorites = _favorites.asStateFlow()

    private var originalCoins: List<Coin> = emptyList()

    // Debounce trigger
    private val loadTrigger = MutableStateFlow(0)

    init {
        observeLoadTrigger()
        loadCoins() // initial load
        loadFavorites()
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadTrigger() {
        viewModelScope.launch {
            loadTrigger
                .debounce(300.milliseconds)
                .collect {
                    val list = repository.fetchCoins()
                    originalCoins = list
                    sort(_sortType.value)
                }
        }
    }

    fun loadCoins() {
        loadTrigger.value++ // triggers debounce
    }

    // Load favorites from SQLDelight
    fun loadFavorites() {
        _favorites.value = FavoritesSet(repository.getFavorites().toSet())
    }

    // Toggle favorite and refresh state
    fun toggleFavorite(id: String) {
        repository.toggleFavorite(id)
        loadFavorites()
    }

    fun isFavorite(id: String): Boolean =
        _favorites.value.contains(id)

    fun sort(sortType: SortType) {
        _sortType.value = sortType
        _coins.value = when (sortType) {
            SortType.PRICE -> originalCoins.sortedByDescending { it.currentPrice }
            SortType.CHANGE -> originalCoins.sortedByDescending { it.priceChangeWith24h ?: 0.0 }
            SortType.VOLUME -> originalCoins.sortedByDescending { it.totalVolume ?: 0.0 }
            SortType.MARKET_CAP -> originalCoins.sortedByDescending { it.marketCap ?: 0.0 }
        }
    }
}

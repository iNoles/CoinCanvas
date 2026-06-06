package org.jonathansteele.coincanvas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.data.model.FavoritesSet
import org.jonathansteele.coincanvas.data.repository.CryptoRepository

class CryptoViewModel(
    private val repository: CryptoRepository
) : ViewModel() {
    private val _coins = MutableStateFlow<List<Coin>>(emptyList())
    val coins = _coins.asStateFlow()

    private val _favorites = MutableStateFlow(FavoritesSet(emptySet()))
    val favorites = _favorites.asStateFlow()

    private var originalCoins: List<Coin> = emptyList()

    private val loadTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        // 1. Load cached coins instantly
        val cached = repository.getCachedCoins()
        if (cached.isNotEmpty()) {
               originalCoins = cached
        }

        // 2. Start observing triggers
        observeLoadTrigger()

        // 3. Fetch fresh coins (debounced)
        loadCoins()

        // 4. Load favorites
        loadFavorites()
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadTrigger() {
        viewModelScope.launch {
            loadTrigger
                .debounce(300.milliseconds)
                .collectLatest {
                    val list = repository.fetchCoins()
                    originalCoins = list
                }
        }
    }

    fun loadCoins() {
        loadTrigger.tryEmit(Unit)
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = FavoritesSet(repository.getFavorites().toSet())
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            repository.toggleFavorite(id)
            loadFavorites()
        }
    }

    //fun isFavorite(id: String): Boolean = _favorites.value.contains(id)
}

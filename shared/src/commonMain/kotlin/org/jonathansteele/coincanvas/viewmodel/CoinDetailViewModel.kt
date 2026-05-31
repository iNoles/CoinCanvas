package org.jonathansteele.coincanvas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jonathansteele.coincanvas.CoinDetailState
import org.jonathansteele.coincanvas.data.repository.CryptoRepository

class CoinDetailViewModel(
    private val repository: CryptoRepository,
    private val coinId: String
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        loadCoin()
    }

    private fun loadCoin() {
        viewModelScope.launch {
            val coin = repository.getCoinById(coinId)
            val history = repository.getHistoryForCoin(coinId)

            _state.value = CoinDetailState(
                coin = coin,
                history = history,
                isLoading = false
            )
        }
    }
}

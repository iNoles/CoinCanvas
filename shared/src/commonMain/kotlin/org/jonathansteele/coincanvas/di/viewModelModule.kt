package org.jonathansteele.coincanvas.di

import org.jonathansteele.coincanvas.viewmodel.CoinDetailViewModel
import org.jonathansteele.coincanvas.viewmodel.CryptoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::CryptoViewModel)

    viewModel { (coinId: String) ->
        CoinDetailViewModel(
            repository = get(),
            coinId = coinId
        )
    }
}

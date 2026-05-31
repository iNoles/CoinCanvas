package org.jonathansteele.coincanvas

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jonathansteele.coincanvas.data.api.CryptoApi
import org.jonathansteele.coincanvas.data.repository.CryptoRepository
import org.jonathansteele.coincanvas.viewmodel.CoinDetailViewModel
import org.jonathansteele.coincanvas.viewmodel.CryptoViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    singleOf(::CryptoApi)
    singleOf(::CryptoRepository)
    viewModelOf(::CryptoViewModel)
    viewModel { (coinId: String) ->
        CoinDetailViewModel(
            repository = get(),
            coinId = coinId
        )
    }
}
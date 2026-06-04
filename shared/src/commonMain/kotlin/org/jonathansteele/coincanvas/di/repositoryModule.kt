package org.jonathansteele.coincanvas.di

import org.jonathansteele.coincanvas.data.repository.CryptoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::CryptoRepository)
}

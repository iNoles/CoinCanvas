package org.jonathansteele.coincanvas.di

import org.jonathansteele.coincanvas.Database
import org.koin.core.module.Module
import org.koin.dsl.module

expect val sqlDriverModule: Module

val databaseModule = module {
    single {
        Database(get())
    }
    single { get<Database>().coinsQueries }
    single { get<Database>().favoriteQueries }
}

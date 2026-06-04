package org.jonathansteele.coincanvas.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.jonathansteele.coincanvas.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqlDriverModule: Module = module {
    single {
        AndroidSqliteDriver(
            Database.Schema,
            get(),
            "coincanvas.db"
        )
    }
}

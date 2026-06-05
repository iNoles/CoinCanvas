package org.jonathansteele.coincanvas.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.jonathansteele.coincanvas.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema, "coincanvas.db")
    }
}
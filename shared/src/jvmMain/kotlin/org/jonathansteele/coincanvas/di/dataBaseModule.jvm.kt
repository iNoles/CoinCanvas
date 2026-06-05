package org.jonathansteele.coincanvas.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties
import org.jonathansteele.coincanvas.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        JdbcSqliteDriver(
            "jdbc:sqlite:coincanvas.db",
            Properties(),
            Database.Schema
        )
    }
}
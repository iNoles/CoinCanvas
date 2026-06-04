package org.jonathansteele.coincanvas.di

import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqlDriverModule: Module = module {
    single {
        createDefaultWebWorkerDriver()
    }
}

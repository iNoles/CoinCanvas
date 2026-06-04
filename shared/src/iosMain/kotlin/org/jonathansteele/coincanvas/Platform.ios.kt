package org.jonathansteele.coincanvas

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

@Composable
actual fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme? = null

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "coincanvas.db")
    }
}
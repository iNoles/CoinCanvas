package org.jonathansteele.coincanvas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jonathansteele.coincanvas.di.appModules
import org.jonathansteele.coincanvas.theme.CoinCanvasTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
fun App() {
    KoinApplication(configuration = koinConfiguration {
        modules(appModules)
    }) {
        CoinCanvasTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SinglePaneNavigation()
            }
        }
    }
}


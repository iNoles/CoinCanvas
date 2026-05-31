package org.jonathansteele.coincanvas.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.jonathansteele.coincanvas.getDynamicColorScheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CoinCanvasTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamicScheme = if (dynamicColor) getDynamicColorScheme(darkTheme) else null
    val colors = dynamicScheme ?: if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialExpressiveTheme(
            colorScheme = colors,
            typography = CoinCanvasTypography,
            shapes = CoinCanvasShapes,
            content = content
        )
    }
}

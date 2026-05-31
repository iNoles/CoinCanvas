package org.jonathansteele.coincanvas.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class Spacing(
    val xs: Int = 4,
    val sm: Int = 8,
    val md: Int = 16,
    val lg: Int = 24,
    val xl: Int = 32
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

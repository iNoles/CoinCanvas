package org.jonathansteele.coincanvas

import androidx.compose.runtime.Composable

@Composable
expect fun getDynamicColorScheme(darkTheme: Boolean): androidx.compose.material3.ColorScheme?

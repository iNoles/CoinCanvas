package org.jonathansteele.coincanvas.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object CoinCanvasColors {

    // Brand
    val Primary = Color(0xFF0A84FF)
    val PrimaryDark = Color(0xFF0060DF)
    val PrimaryLight = Color(0xFF4DB3FF)

    val Secondary = Color(0xFF14B8A6)
    val SecondaryDark = Color(0xFF0D8C7E)
    val SecondaryLight = Color(0xFF5EEAD4)

    // Glow
    val GlowCyan = Color(0xFF00E5FF)
    val GlowBlue = Color(0xFF1E90FF)
    val GlowAqua = Color(0xFF7DF9FF)

    // Chart gradients
    val ChartUpStart = Color(0xFF4DB3FF)
    val ChartUpMid = Color(0xFF0A84FF)
    val ChartUpEnd = Color(0xFF0060DF)

    val ChartDownStart = Color(0xFFFF6B6B)
    val ChartDownMid = Color(0xFFFF3B30)
    val ChartDownEnd = Color(0xFFC1272D)

    // Semantic
    val Success = Color(0xFF00C853)
    val SuccessLight = Color(0xFF69F0AE)
    val Error = Color(0xFFFF3B30)
    val ErrorDark = Color(0xFFC1272D)
    val Warning = Color(0xFFFFB300)
    val Info = Color(0xFF4DB3FF)

    // Dark surfaces
    val SurfaceDark = Color(0xFF0D1117)
    val SurfaceVariantDark = Color(0xFF1A1F25)
    val SurfaceElevatedDark = Color(0xFF161B22)
    val OnSurfaceDark = Color(0xFFE6EDF3)
    val OnSurfaceVariantDark = Color(0xFF9BA7B4)

    // Light surfaces
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceVariantLight = Color(0xFFF2F4F7)
    val SurfaceElevatedLight = Color(0xFFE9ECF1)
    val OnSurfaceLight = Color(0xFF1A1A1A)
    val OnSurfaceVariantLight = Color(0xFF4A4A4A)
}

val DarkColors: ColorScheme = darkColorScheme(
    primary = CoinCanvasColors.Primary,
    onPrimary = Color.White,
    secondary = CoinCanvasColors.Secondary,
    onSecondary = Color.Black,
    background = CoinCanvasColors.SurfaceDark,
    onBackground = CoinCanvasColors.OnSurfaceDark,
    surface = CoinCanvasColors.SurfaceDark,
    onSurface = CoinCanvasColors.OnSurfaceDark,
    surfaceVariant = CoinCanvasColors.SurfaceVariantDark,
    onSurfaceVariant = CoinCanvasColors.OnSurfaceVariantDark,
    error = CoinCanvasColors.Error,
    onError = Color.White
)

val LightColors: ColorScheme = lightColorScheme(
    primary = CoinCanvasColors.PrimaryDark,
    onPrimary = Color.White,
    secondary = CoinCanvasColors.SecondaryDark,
    onSecondary = Color.White,
    background = CoinCanvasColors.SurfaceLight,
    onBackground = CoinCanvasColors.OnSurfaceLight,
    surface = CoinCanvasColors.SurfaceLight,
    onSurface = CoinCanvasColors.OnSurfaceLight,
    surfaceVariant = CoinCanvasColors.SurfaceVariantLight,
    onSurfaceVariant = CoinCanvasColors.OnSurfaceVariantLight,
    error = CoinCanvasColors.Error,
    onError = Color.White
)

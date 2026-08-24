package `in`.vegamdigital.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Navy = Color(0xFF111936)
val NavySoft = Color(0xFF202A4A)
val BrandBlue = Color(0xFF347FF1)
val Gold = Color(0xFFFFBC53)
val Paper = Color(0xFFF4F6FB)
val Ink = Color(0xFF18213B)
val Muted = Color(0xFF74809B)
val Mint = Color(0xFF50B992)
val PaleMint = Color(0xFFEAF8F2)

private val colors = lightColorScheme(
    primary = BrandBlue, onPrimary = Color.White, secondary = Mint,
    background = Paper, onBackground = Ink, surface = Color.White, onSurface = Ink,
    outline = Color(0xFFDDE3EF), tertiary = Gold
)

private val typography = Typography(
    headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 30.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
)

@Composable fun VegamTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colors, typography = typography, content = content)
}

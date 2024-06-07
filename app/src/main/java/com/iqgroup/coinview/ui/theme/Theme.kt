package com.iqgroup.coinview.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2D5270), // Adjusted primary color for dark mode
    primaryContainer = Color(0xFF4D5B85), // Adjusted primary container color for dark mode
    secondary = Color(0xFF4D6B4F), // Adjusted secondary color for dark mode
    secondaryContainer = Color(0xFF465347), // Adjusted secondary container color for dark mode
    tertiaryContainer = Color(0xFF5F3A3A), // Adjusted tertiary container color for dark mode
    tertiary = Color(0xFF72525D), // Adjusted tertiary color for dark mode
    onTertiaryContainer = Color(0xFFD7CCC8),
    background = Color(0xFF121212), // Dark background color
    surface = Color(0xFF333333), // Dark surface color
    error = Color(0xFFEF5350), // Adjusted error color for dark mode
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFFFFFFFF)
)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8E9A9B),
    primaryContainer = Color(0xFFA7A7A7),
    secondary = Color(0xFF8E9A9B),
    secondaryContainer = Color(0xFF6B8688),
    tertiaryContainer = Color(0xFFA1887F),
    tertiary = Color(0xFFCFACA0),
    onTertiaryContainer = Color(0xFFD7CCC8),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF2F2F2),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun CoinViewTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic coloring seems like it is not within our immediate reach,
        // so we are using manual.

        /*
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        */

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
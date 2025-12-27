package com.example.mymess.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color // Added

private val DarkColorScheme = darkColorScheme(
    primary = CorporateBlue, // Brighter in dark mode
    secondary = CorporateNavy,
    tertiary = CorporateSlate,
    background = Color(0xFF121212), // True Dark
    surface = Color(0xFF1E1E1E), // Slightly lighter surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = CorporateNavy,
    secondary = CorporateBlue,
    tertiary = CorporateSlate,
    background = CorporateLightGray,
    surface = CorporateWhite,
    onPrimary = CorporateWhite,
    onSecondary = CorporateWhite,
    onTertiary = CorporateWhite,
    onBackground = CorporateNavy,
    onSurface = CorporateNavy
)

@Composable
fun MyMessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // "Modern Tech": Enable Dynamic Color (Monet) by default for Android 12+ users
    // This makes the app feel "native" and personalized.
    dynamicColor: Boolean = true, 
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

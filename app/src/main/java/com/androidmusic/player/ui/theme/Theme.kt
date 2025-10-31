package com.androidmusic.player.ui.theme

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
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ExpressivePrimary,
    onPrimary = ExpressiveOnPrimary,
    primaryContainer = ExpressivePrimaryContainer,
    onPrimaryContainer = ExpressiveOnPrimaryContainer,
    
    secondary = ExpressiveSecondary,
    onSecondary = ExpressiveOnSecondary,
    secondaryContainer = ExpressiveSecondaryContainer,
    onSecondaryContainer = ExpressiveOnSecondaryContainer,
    
    tertiary = ExpressiveTertiary,
    onTertiary = ExpressiveOnTertiary,
    tertiaryContainer = ExpressiveTertiaryContainer,
    onTertiaryContainer = ExpressiveOnTertiaryContainer,
    
    error = ExpressiveError,
    onError = ExpressiveOnError,
    errorContainer = ExpressiveErrorContainer,
    onErrorContainer = ExpressiveOnErrorContainer,
    
    background = ExpressiveBackground,
    onBackground = ExpressiveOnBackground,
    surface = ExpressiveSurface,
    onSurface = ExpressiveOnSurface,
    surfaceVariant = ExpressiveSurfaceVariant,
    onSurfaceVariant = ExpressiveOnSurfaceVariant,
    outline = ExpressiveOutline,
    outlineVariant = ExpressiveOutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = ExpressivePrimary,
    onPrimary = ExpressiveOnPrimary,
    primaryContainer = ExpressivePrimaryContainer,
    onPrimaryContainer = ExpressiveOnPrimaryContainer,
    
    secondary = ExpressiveSecondary,
    onSecondary = ExpressiveOnSecondary,
    secondaryContainer = ExpressiveSecondaryContainer,
    onSecondaryContainer = ExpressiveOnSecondaryContainer,
    
    tertiary = ExpressiveTertiary,
    onTertiary = ExpressiveOnTertiary,
    tertiaryContainer = ExpressiveTertiaryContainer,
    onTertiaryContainer = ExpressiveOnTertiaryContainer,
    
    error = ExpressiveError,
    onError = ExpressiveOnError,
    errorContainer = ExpressiveErrorContainer,
    onErrorContainer = ExpressiveOnErrorContainer,
    
    background = ExpressiveBackground,
    onBackground = ExpressiveOnBackground,
    surface = ExpressiveSurface,
    onSurface = ExpressiveOnSurface,
    surfaceVariant = ExpressiveSurfaceVariant,
    onSurfaceVariant = ExpressiveOnSurfaceVariant,
    outline = ExpressiveOutline,
    outlineVariant = ExpressiveOutlineVariant
)

@Composable
fun AndroidMusicTheme(
    darkTheme: Boolean = true, // Force dark theme for new design
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors
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
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

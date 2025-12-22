package com.example.socialapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Success,
    secondary = StreakPrimary,
    tertiary = ActivityHigh,
    background = BackgroundPrimary,
    surface = SurfaceElevated,
    onPrimary = BackgroundPrimary,
    onSecondary = TextPrimary,
    onTertiary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = Error,
    surfaceVariant = SurfaceElevatedHigher,
    onSurfaceVariant = TextSecondary
)

@Composable
fun SocialAppTheme(
    darkTheme: Boolean = true,  // Always use dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,  // Disable dynamic color to use our custom design system
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
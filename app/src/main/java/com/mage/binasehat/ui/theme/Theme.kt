package com.mage.binasehat.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mage.binasehat.ui.util.UserPreferences

val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.Black,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = WhitePrimary,
    surface = WhitePrimary,
    onSurface = Color.Black,
    primaryContainer = WhiteComponentPrimary,
    surfaceTint = GrayComponentPrimary

)

val DarkColorScheme = darkColorScheme(
    primary = DarkGreenPrimary,
    onPrimary = Color.White,
    secondary = DarkGreenSecondary,
    tertiary = DarkGreenTertiary,
    background = DarkGreenBackground,
    surface = DarkGreenBackground,
    onSurface = Color.White,
    primaryContainer = DarkComponentPrimary,
    surfaceTint = DarkGrayComponentPrimary
)

@Composable
fun BinaSehatTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = false,  // Enable dynamic colors for Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Use dynamic colors if enabled and running on Android 12+
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

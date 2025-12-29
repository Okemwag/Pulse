package com.okemwag.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Pulse Theme
 * 
 * A beautiful, modern Material 3 theme featuring eucalyptus greens,
 * golden accents, and a friendly koala-inspired aesthetic.
 */

// ============================================
// Light Color Scheme
// ============================================
private val LightColorScheme = lightColorScheme(
    // Primary
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    
    // Secondary
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    
    // Tertiary
    tertiary = TertiaryLight,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    
    // Background & Surface
    background = BackgroundLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = SecondaryLight,
    
    // Error
    error = ErrorLight,
    onError = Color.White,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    
    // Outline
    outline = SecondaryLight,
    outlineVariant = SurfaceVariantLight,
    
    // Inverse
    inverseSurface = SurfaceDark,
    inverseOnSurface = OnSurfaceDark,
    inversePrimary = PrimaryDark,
    
    // Other
    scrim = Color.Black.copy(alpha = 0.32f),
    surfaceTint = PrimaryLight
)

// ============================================
// Dark Color Scheme
// ============================================
private val DarkColorScheme = darkColorScheme(
    // Primary
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = PrimaryContainer,
    
    // Secondary
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = SecondaryContainer,
    
    // Tertiary
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryContainer,
    tertiaryContainer = TertiaryLight,
    onTertiaryContainer = TertiaryContainer,
    
    // Background & Surface
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = SecondaryDark,
    
    // Error
    error = ErrorDark,
    onError = OnErrorContainer,
    errorContainer = ErrorLight,
    onErrorContainer = ErrorContainer,
    
    // Outline
    outline = SecondaryDark,
    outlineVariant = SurfaceVariantDark,
    
    // Inverse
    inverseSurface = SurfaceLight,
    inverseOnSurface = OnSurfaceLight,
    inversePrimary = PrimaryLight,
    
    // Other
    scrim = Color.Black.copy(alpha = 0.32f),
    surfaceTint = PrimaryDark
)

// ============================================
// Extended Colors (for custom components)
// ============================================
data class PulseExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val shimmerBase: Color,
    val shimmerHighlight: Color,
    val glassBackground: Color,
    val glassBorder: Color
)

private val LightExtendedColors = PulseExtendedColors(
    success = SuccessLight,
    onSuccess = Color.White,
    successContainer = SuccessContainer,
    warning = WarningLight,
    onWarning = Color.White,
    warningContainer = WarningContainer,
    shimmerBase = ShimmerBase,
    shimmerHighlight = ShimmerHighlight,
    glassBackground = GlassBackground,
    glassBorder = GlassBorder
)

private val DarkExtendedColors = PulseExtendedColors(
    success = SuccessDark,
    onSuccess = Color.Black,
    successContainer = SuccessLight,
    warning = WarningDark,
    onWarning = Color.Black,
    warningContainer = WarningLight,
    shimmerBase = Color(0xFF2A2A2A),
    shimmerHighlight = Color(0xFF3D3D3D),
    glassBackground = Color(0x33000000),
    glassBorder = Color(0x33FFFFFF)
)

val LocalPulseColors = staticCompositionLocalOf { LightExtendedColors }

// ============================================
// Theme Composable
// ============================================
@Composable
fun PulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ but we prefer our custom colors
    dynamicColor: Boolean = false,
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
    
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar color
            window.statusBarColor = Color.Transparent.toArgb()
            // Set navigation bar color
            window.navigationBarColor = Color.Transparent.toArgb()
            // Configure light/dark status bar icons
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalPulseColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PulseTypography,
            shapes = PulseShapes,
            content = content
        )
    }
}

// ============================================
// Theme Extensions
// ============================================
object PulseTheme {
    val extendedColors: PulseExtendedColors
        @Composable
        get() = LocalPulseColors.current
}

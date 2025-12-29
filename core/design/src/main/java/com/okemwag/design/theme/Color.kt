package com.okemwag.design.theme

import androidx.compose.ui.graphics.Color

/**
 * Pulse Premium Color Palette
 * 
 * A vibrant, modern color scheme inspired by nature and the koala mascot.
 * Features eucalyptus greens, warm golds, and soft grays.
 */

// ============================================
// Primary Colors - Eucalyptus Green
// ============================================
val PrimaryLight = Color(0xFF2D5A27)      // Deep forest green
val PrimaryDark = Color(0xFF8BC34A)       // Bright lime green
val PrimaryContainer = Color(0xFFB7E9B1)  // Soft mint
val OnPrimaryContainer = Color(0xFF002105) // Deep green text

// ============================================
// Secondary Colors - Koala Gray
// ============================================
val SecondaryLight = Color(0xFF5D6D61)    // Warm gray-green
val SecondaryDark = Color(0xFFA8C4AD)     // Light sage
val SecondaryContainer = Color(0xFFD0E8D4) // Pale mint
val OnSecondaryContainer = Color(0xFF1A291E) // Dark gray text

// ============================================
// Tertiary Colors - Golden Accent
// ============================================
val TertiaryLight = Color(0xFFF5A623)     // Warm golden
val TertiaryDark = Color(0xFFFFD180)      // Light amber
val TertiaryContainer = Color(0xFFFFECB3) // Pale gold
val OnTertiaryContainer = Color(0xFF3E2723) // Deep brown text

// ============================================
// Surface & Background Colors
// ============================================
// Light Theme
val SurfaceLight = Color(0xFFFAFDFA)      // Almost white with green tint
val BackgroundLight = Color(0xFFF5F9F6)   // Soft off-white
val SurfaceVariantLight = Color(0xFFE0E8E2) // Light gray-green

// Dark Theme
val SurfaceDark = Color(0xFF0F1F14)       // Deep forest at night
val BackgroundDark = Color(0xFF0A1610)    // Darkest green-black
val SurfaceVariantDark = Color(0xFF1E2D23) // Dark sage

// ============================================
// Error, Warning, Success Colors
// ============================================
val ErrorLight = Color(0xFFBA1A1A)
val ErrorDark = Color(0xFFFFB4AB)
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF410002)

val SuccessLight = Color(0xFF1B8755)
val SuccessDark = Color(0xFF6DD58C)
val SuccessContainer = Color(0xFFC7F2D0)

val WarningLight = Color(0xFFE65100)
val WarningDark = Color(0xFFFFB74D)
val WarningContainer = Color(0xFFFFE0B2)

// ============================================
// Text Colors
// ============================================
val OnPrimaryLight = Color(0xFFFFFFFF)
val OnPrimaryDark = Color(0xFF003909)
val OnSecondaryLight = Color(0xFFFFFFFF)
val OnSecondaryDark = Color(0xFF11201A)
val OnSurfaceLight = Color(0xFF191C1A)
val OnSurfaceDark = Color(0xFFE0E3E0)

// ============================================
// Gradient Colors
// ============================================
object PulseGradients {
    // Primary gradient for buttons and headers
    val primaryGradient = listOf(
        Color(0xFF2D5A27),
        Color(0xFF4CAF50)
    )
    
    // Splash screen gradient
    val splashGradient = listOf(
        Color(0xFF1B4332),
        Color(0xFF2D6A4F),
        Color(0xFF40916C)
    )
    
    // Golden accent gradient
    val accentGradient = listOf(
        Color(0xFFF5A623),
        Color(0xFFFFD54F)
    )
    
    // Dark mode gradient
    val darkGradient = listOf(
        Color(0xFF0A1610),
        Color(0xFF1B3D2F)
    )
    
    // Card glassmorphism overlay
    val glassOverlay = listOf(
        Color(0x33FFFFFF),
        Color(0x0DFFFFFF)
    )
}

// ============================================
// Special Effect Colors
// ============================================
val ShimmerBase = Color(0xFFE0E0E0)
val ShimmerHighlight = Color(0xFFF5F5F5)
val GlassBackground = Color(0x4DFFFFFF)  // 30% white
val GlassBorder = Color(0x33FFFFFF)      // 20% white

// ============================================
// Koala Theme Special Colors
// ============================================
object KoalaColors {
    val koalaGray = Color(0xFF8B9D98)
    val koalaNose = Color(0xFF2A2A2A)
    val eucalyptusGreen = Color(0xFF4CAF50)
    val eucalyptusLeaf = Color(0xFF81C784)
    val treeBark = Color(0xFF5D4037)
    val softFur = Color(0xFFBDBDBD)
}

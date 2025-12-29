package com.okemwag.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Pulse Shape System
 * 
 * Modern, rounded shapes that create a friendly and approachable feel.
 * Inspired by the soft, cuddly nature of our koala mascot.
 */

val PulseShapes = Shapes(
    // Extra small - chips, small buttons
    extraSmall = RoundedCornerShape(4.dp),
    
    // Small - buttons, text fields
    small = RoundedCornerShape(8.dp),
    
    // Medium - cards, dialogs
    medium = RoundedCornerShape(16.dp),
    
    // Large - bottom sheets, large cards
    large = RoundedCornerShape(24.dp),
    
    // Extra large - hero sections, modals
    extraLarge = RoundedCornerShape(32.dp)
)

/**
 * Custom shape definitions for specific UI elements
 */
object PulseShapeTokens {
    // Pill shape for badges and chips
    val pill = RoundedCornerShape(50)
    
    // Top rounded for bottom sheets
    val topRounded = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    
    // Bottom rounded for app bars
    val bottomRounded = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 24.dp,
        bottomEnd = 24.dp
    )
    
    // Asymmetric for unique cards
    val asymmetric = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 8.dp,
        bottomStart = 8.dp,
        bottomEnd = 24.dp
    )
    
    // Splash screen container
    val splash = RoundedCornerShape(32.dp)
    
    // Floating action button
    val fab = RoundedCornerShape(16.dp)
    
    // Navigation bar indicator
    val navIndicator = RoundedCornerShape(20.dp)
    
    // Avatar/profile picture
    val avatar = RoundedCornerShape(50)
    
    // Notification badge
    val badge = RoundedCornerShape(10.dp)
    
    // Search bar
    val searchBar = RoundedCornerShape(28.dp)
}

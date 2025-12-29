package com.okemwag.design.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.okemwag.design.theme.ShimmerBase
import com.okemwag.design.theme.ShimmerHighlight

/**
 * Shimmer Loading Effect Components
 * 
 * Beautiful loading placeholders with animated shimmer effect
 * for creating pleasing skeleton screens.
 */

/**
 * Animated shimmer modifier that can be applied to any composable
 */
@Composable
fun Modifier.shimmerEffect(
    isLoading: Boolean = true,
    baseColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    highlightColor: Color = MaterialTheme.colorScheme.surface
): Modifier {
    if (!isLoading) return this
    
    val shimmerColors = listOf(
        baseColor,
        highlightColor,
        baseColor
    )
    
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )
    
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation - 500f, 0f),
        end = Offset(translateAnimation, 0f)
    )
    
    return this.background(brush)
}

/**
 * Shimmer box placeholder
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    width: Dp = 100.dp,
    height: Dp = 20.dp,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Box(
        modifier = modifier
            .size(width, height)
            .clip(shape)
            .shimmerEffect()
    )
}

/**
 * Circular shimmer placeholder (for avatars)
 */
@Composable
fun ShimmerCircle(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .shimmerEffect()
    )
}

/**
 * Text line shimmer placeholder
 */
@Composable
fun ShimmerTextLine(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    height: Dp = 16.dp
) {
    ShimmerBox(
        modifier = modifier,
        width = width,
        height = height,
        shape = RoundedCornerShape(4.dp)
    )
}

/**
 * Card skeleton placeholder
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ShimmerCircle(size = 40.dp)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerTextLine(width = 120.dp, height = 14.dp)
                ShimmerTextLine(width = 80.dp, height = 12.dp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ShimmerBox(
            modifier = Modifier.fillMaxWidth(),
            width = Dp.Unspecified,
            height = 120.dp,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ShimmerTextLine(width = 250.dp, height = 14.dp)
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerTextLine(width = 180.dp, height = 12.dp)
    }
}

/**
 * List item skeleton placeholder
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier,
    showImage: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showImage) {
            ShimmerBox(
                width = 60.dp,
                height = 60.dp,
                shape = RoundedCornerShape(12.dp)
            )
        }
        
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerTextLine(
                width = 180.dp,
                height = 16.dp
            )
            ShimmerTextLine(
                width = 120.dp,
                height = 12.dp
            )
        }
        
        ShimmerBox(
            width = 60.dp,
            height = 32.dp,
            shape = RoundedCornerShape(8.dp)
        )
    }
}

/**
 * News feed skeleton
 */
@Composable
fun ShimmerNewsFeed(
    modifier: Modifier = Modifier,
    itemCount: Int = 3
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(itemCount) {
            ShimmerCard()
        }
    }
}

/**
 * Profile header skeleton
 */
@Composable
fun ShimmerProfileHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerCircle(size = 100.dp)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ShimmerTextLine(width = 150.dp, height = 20.dp)
        
        Spacer(modifier = Modifier.height(8.dp))
        
        ShimmerTextLine(width = 100.dp, height = 14.dp)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShimmerTextLine(width = 40.dp, height = 24.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    ShimmerTextLine(width = 60.dp, height = 12.dp)
                }
            }
        }
    }
}

/**
 * Category chips skeleton
 */
@Composable
fun ShimmerChips(
    modifier: Modifier = Modifier,
    chipCount: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(chipCount) { index ->
            val width = when (index % 3) {
                0 -> 80.dp
                1 -> 100.dp
                else -> 70.dp
            }
            ShimmerBox(
                width = width,
                height = 36.dp,
                shape = RoundedCornerShape(18.dp)
            )
        }
    }
}

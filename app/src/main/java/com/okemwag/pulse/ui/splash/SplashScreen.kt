package com.okemwag.pulse.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.okemwag.design.theme.PulseGradients
import com.okemwag.design.theme.PulseTextStyles
import com.okemwag.pulse.R
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Captivating Splash Screen with Animated Koala
 * 
 * Features:
 * - Animated koala mascot (Lottie)
 * - Gradient background
 * - Floating eucalyptus leaves
 * - Smooth fade-in animations
 * - Pulsing loading indicator
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    // Lottie animation state
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.koala_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    
    // Koala scale animation
    val koalaScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "koalaScale"
    )
    
    // Title fade animation
    val titleAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 400
        ),
        label = "titleAlpha"
    )
    
    // Tagline fade animation
    val taglineAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 600
        ),
        label = "taglineAlpha"
    )
    
    // Loading dots animation
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val loadingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loadingAlpha"
    )
    
    // Start animation and navigate after delay
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(3000) // Show splash for 3 seconds
        onSplashComplete()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = PulseGradients.splashGradient
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Floating leaves background
        FloatingLeavesBackground()
        
        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Animated Koala
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(koalaScale),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // App Title
            Text(
                text = "PULSE",
                style = PulseTextStyles.splashTitle,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(titleAlpha)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tagline
            Text(
                text = "Community News Platform",
                style = PulseTextStyles.splashTagline,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(taglineAlpha)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(taglineAlpha)
            ) {
                repeat(3) { index ->
                    val dotAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot$index"
                    )
                    LoadingDot(alpha = dotAlpha)
                }
            }
        }
    }
}

@Composable
private fun LoadingDot(alpha: Float) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .alpha(alpha)
            .background(
                color = Color.White,
                shape = androidx.compose.foundation.shape.CircleShape
            )
    )
}

@Composable
private fun FloatingLeavesBackground() {
    val leaves = remember { generateLeaves(15) }
    val infiniteTransition = rememberInfiniteTransition(label = "leaves")
    
    leaves.forEachIndexed { index, leaf ->
        val offsetY by infiniteTransition.animateFloat(
            initialValue = leaf.startY,
            targetValue = leaf.endY,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = leaf.duration,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "leafY$index"
        )
        
        val offsetX by infiniteTransition.animateFloat(
            initialValue = leaf.startX,
            targetValue = leaf.startX + leaf.swayAmount,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = leaf.duration / 4,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "leafX$index"
        )
        
        val rotation by infiniteTransition.animateFloat(
            initialValue = leaf.startRotation,
            targetValue = leaf.endRotation,
            animationSpec = infiniteRepeatable(
                animation = tween(leaf.duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "leafRot$index"
        )
        
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = leaf.color.copy(alpha = leaf.alpha),
                radius = leaf.size,
                center = Offset(offsetX, offsetY)
            )
        }
    }
}

private data class LeafData(
    val startX: Float,
    val startY: Float,
    val endY: Float,
    val size: Float,
    val color: Color,
    val alpha: Float,
    val swayAmount: Float,
    val startRotation: Float,
    val endRotation: Float,
    val duration: Int
)

private fun generateLeaves(count: Int): List<LeafData> {
    val leafColors = listOf(
        Color(0xFF81C784), // Light green
        Color(0xFF66BB6A), // Medium green
        Color(0xFF4CAF50), // Green
        Color(0xFFA5D6A7), // Pale green
        Color(0xFF388E3C)  // Dark green
    )
    
    return List(count) {
        LeafData(
            startX = Random.nextFloat() * 1080f,
            startY = Random.nextFloat() * -200f - 50f,
            endY = 2000f + Random.nextFloat() * 200f,
            size = Random.nextFloat() * 15f + 8f,
            color = leafColors.random(),
            alpha = Random.nextFloat() * 0.3f + 0.1f,
            swayAmount = Random.nextFloat() * 100f - 50f,
            startRotation = Random.nextFloat() * 360f,
            endRotation = Random.nextFloat() * 720f - 360f,
            duration = (Random.nextFloat() * 8000f + 6000f).toInt()
        )
    }
}

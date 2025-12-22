package com.example.socialapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit
) {
    // Auto-navigate after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Gradient circles with animations
            AnimatedLogo()

            // FLOW text
            Text(
                text = "FLOW",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(200.dp))

            // Animated loading dots
            LoadingDots()
        }
    }
}

@Composable
private fun AnimatedLogo() {
    // Initial pop animation
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // Continuous floating animation
    val infiniteTransition = rememberInfiniteTransition(label = "float")

    val float by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // Subtle rotation wobble
    val rotation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    // Pulsing scale effect
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .offset(y = (-float).dp)
            .graphicsLayer {
                scaleX = scale * pulse
                scaleY = scale * pulse
                rotationZ = rotation
            },
        contentAlignment = Alignment.Center
    ) {
        // Glow effect layers for left circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset(x = (-15).dp)
                .blur(20.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x807B9EF5),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Left circle (blue to purple gradient) with more color stops
        Box(
            modifier = Modifier
                .size(70.dp)
                .offset(x = (-15).dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFF7B9EF5),
                    spotColor = Color(0xFF9B7BF5)
                )
                .background(
                    brush = Brush.radialGradient(
                        0.0f to Color(0xFFAEC8FF),
                        0.3f to Color(0xFF7B9EF5),
                        0.7f to Color(0xFF9B7BF5),
                        1.0f to Color(0xFF7A5FD9)
                    ),
                    shape = CircleShape
                )
        )

        // Glow effect layers for right circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset(x = 15.dp)
                .blur(20.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x80E87BF5),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Right circle (pink to purple gradient) with more color stops
        Box(
            modifier = Modifier
                .size(70.dp)
                .offset(x = 15.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFFE87BF5),
                    spotColor = Color(0xFFC77BF5)
                )
                .background(
                    brush = Brush.radialGradient(
                        0.0f to Color(0xFFFFAEF0),
                        0.3f to Color(0xFFE87BF5),
                        0.7f to Color(0xFFC77BF5),
                        1.0f to Color(0xFFA960D9)
                    ),
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ),
                label = "dot$index"
            )

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = Color.White.copy(alpha = alpha),
                        shape = CircleShape
                    )
            )
        }
    }
}

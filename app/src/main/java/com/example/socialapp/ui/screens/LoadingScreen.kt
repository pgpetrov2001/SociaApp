package com.example.socialapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    onNavigateNext: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var loadingTextIndex by remember { mutableStateOf(0) }

    val loadingTexts = listOf(
        "Analyzing your goals...",
        "Building your personalized plan...",
        "Calibrating success metrics...",
        "Preparing your journey..."
    )

    // Progress animation
    LaunchedEffect(Unit) {
        val totalDuration = 20000L // 20 seconds
        val steps = 100
        val delayPerStep = totalDuration / steps

        repeat(steps) { step ->
            delay(delayPerStep)
            progress = (step + 1) / steps.toFloat()

            // Change text every 5 seconds
            if (step % 25 == 0 && step > 0) {
                loadingTextIndex = (loadingTextIndex + 1) % loadingTexts.size
            }
        }

        onNavigateNext()
    }

    // Animated gradient background shimmer
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated loading text
            Text(
                text = loadingTexts[loadingTextIndex],
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Progress bar container
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Custom gradient progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF1C1C1E))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF7B9EF5),
                                        Color(0xFF9B7BF5),
                                        Color(0xFFE87BF5)
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Percentage text
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF8E8E93)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Subtle hint text
            Text(
                text = "Crafting your personalized experience",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6E6E73),
                textAlign = TextAlign.Center
            )
        }
    }
}

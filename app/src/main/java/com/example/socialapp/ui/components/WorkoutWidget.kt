package com.example.socialapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.*

/**
 * Widget 1: Today's conversation count with circular progress
 *
 * Circle color logic:
 * - White: when todayCount < dailyGoal
 * - Green: when todayCount >= dailyGoal (quota met)
 */
@Composable
fun TodayWidget(
    todayCount: Int,
    dailyGoal: Int,
    modifier: Modifier = Modifier
) {
    // Calculate progress
    val targetProgress = if (dailyGoal > 0) {
        (todayCount.toFloat() / dailyGoal).coerceIn(0f, 1f)
    } else 0f

    // Determine if quota is met
    val isQuotaMet = todayCount >= dailyGoal && dailyGoal > 0

    // Determine circle color based on quota completion
    val circleColor = if (isQuotaMet) {
        Color(0xFF39D98A) // Green when quota is met
    } else {
        Color.White // White when quota is not met
    }

    // Animated progress for smooth transitions
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(targetProgress) {
        animatedProgress.animateTo(
            targetValue = targetProgress,
            animationSpec = tween(durationMillis = 500)
        )
    }

    WidgetCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = Spacing.xs),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular progress with number
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(bottom = Spacing.sm)
            ) {
                // Progress ring
                Canvas(modifier = Modifier.size(100.dp)) {
                    val strokeWidth = 5.dp.toPx() // Slimmer circle like workout app

                    // Background circle
                    drawCircle(
                        color = Color.White.copy(alpha = 0.1f),
                        radius = (size.minDimension - strokeWidth) / 2,
                        style = Stroke(width = strokeWidth)
                    )

                    // Progress arc with animated progress and dynamic color
                    drawArc(
                        color = circleColor,
                        startAngle = -90f,
                        sweepAngle = 360f * animatedProgress.value,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Number - iOS style like workout app
                Text(
                    text = todayCount.toString(),
                    fontSize = 46.sp,
                    fontWeight = FontWeight.W600,
                    letterSpacing = (-1).sp,
                    color = TextPrimary
                )
            }

            // Label - medium weight like workout app
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                ),
                color = TextSecondary
            )
        }
    }
}

package com.example.socialapp.ui.components

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
 */
@Composable
fun TodayWidget(
    todayCount: Int,
    dailyGoal: Int,
    modifier: Modifier = Modifier
) {
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
                    val strokeWidth = 10.dp.toPx()

                    // Background circle
                    drawCircle(
                        color = Color.White.copy(alpha = 0.1f),
                        radius = (size.minDimension - strokeWidth) / 2,
                        style = Stroke(width = strokeWidth)
                    )

                    // Progress arc
                    val progress = if (dailyGoal > 0) (todayCount.toFloat() / dailyGoal).coerceIn(0f, 1f) else 0f
                    drawArc(
                        color = Color(0xFF39D98A),
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Number
                Text(
                    text = todayCount.toString(),
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
            }

            // Label
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

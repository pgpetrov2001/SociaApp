package com.example.socialapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.*

/**
 * Widget 3: Activity History widget (full width)
 */
@Composable
fun ActivityHistoryWidget(
    activeDays: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    WidgetCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Header with indicator dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Activity History",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Past 60 days performance",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }

                // Three indicator dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(ActivityHigh)
                        )
                    }
                }
            }

            // Timeline labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "2 months ago",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            // Activity dots grid
            ActivityDotsGrid(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ActivityDotsGrid(modifier: Modifier = Modifier) {
    // Generate 60 days of continuous activity data
    val activityData = generateActivityData(60)
    val rows = 5
    val cols = 12 // 60 days ÷ 5 rows = 12 columns

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Create 5 rows of dots
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                // Create 12 columns per row
                for (col in 0 until cols) {
                    val index = row * cols + col
                    if (index < activityData.size) {
                        ActivityDot(activity = activityData[index])
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityDot(activity: Int) {
    // Map activity level to color intensity
    val color = when (activity) {
        0 -> BackgroundTertiary              // No activity - dark gray
        1, 2 -> ActivityLow                  // Low activity (1-2) - very dark green
        3, 4 -> ActivityMedium               // Medium activity (3-4) - medium green
        in 5..7 -> ActivityHigh              // High activity (5-7) - bright green
        else -> ActivityMax                  // Very high (8+) - brightest green
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color)
    )
}

// Helper function to generate random activity data (0-10 interactions per day)
private fun generateActivityData(days: Int): List<Int> {
    return List(days) {
        // Generate realistic distribution: more days with low activity, fewer with high
        when ((0..100).random()) {
            in 0..20 -> 0           // 20% no activity
            in 21..45 -> (1..2).random()   // 25% low activity
            in 46..70 -> (3..4).random()   // 25% medium activity
            in 71..90 -> (5..7).random()   // 20% high activity
            else -> (8..12).random()        // 10% very high activity
        }
    }
}

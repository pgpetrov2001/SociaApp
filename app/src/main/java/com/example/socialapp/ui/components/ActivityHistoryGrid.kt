package com.example.socialapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.data.SocialStats
import com.example.socialapp.ui.theme.*

@Composable
fun ActivityHistorySection(stats: SocialStats) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceElevated
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header with dots indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Activity History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Past 90 days performance",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }

                // Three dots indicator
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ActivityHigh)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Timeline labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "3 months ago",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Text(
                    text = "Today",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Activity grid
            ActivityGrid(activityData = stats.activityHistory.map { it.isActive })

            Spacer(modifier = Modifier.height(24.dp))

            // Active days count
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stats.activeDays.toString(),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "active days",
                        fontSize = 16.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityGrid(activityData: List<Boolean>) {
    // Calculate the number of rows needed
    val columns = 13 // Approximately 90 days / 7 rows ≈ 13 columns
    val rows = 7

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row + (col * rows)
                    if (index < activityData.size) {
                        ActivityDot(isActive = activityData[index])
                    } else {
                        // Empty space for alignment
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityDot(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(if (isActive) ActivityHigh else SurfaceElevatedHigher)
    )
}

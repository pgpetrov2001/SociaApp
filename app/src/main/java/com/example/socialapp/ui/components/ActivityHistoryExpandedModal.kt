package com.example.socialapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.data.MonthActivity
import com.example.socialapp.data.SocialStats
import com.example.socialapp.ui.theme.*
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ActivityHistoryExpandedModal(
    isVisible: Boolean,
    stats: SocialStats,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(250)),
        exit = fadeOut(animationSpec = tween(250))
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Background overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onDismiss() }
            )
            // Modal Content (not blurred)
            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(
                    initialScale = 0.9f,
                    animationSpec = tween(250)
                ) + fadeIn(animationSpec = tween(250)),
                exit = scaleOut(
                    targetScale = 0.9f,
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(200))
            ) {
                ExpandedModalContent(
                    stats = stats,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
private fun ExpandedModalContent(
    stats: SocialStats,
    onDismiss: () -> Unit
) {
    val monthlyActivity = stats.getMonthlyActivity(6)

    Box(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .wrapContentHeight()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(CornerRadius.xl),
                spotColor = Color.Black.copy(alpha = 0.5f)
            )
            .clip(RoundedCornerShape(CornerRadius.xl))
            .background(SurfaceElevatedHigher)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* Prevent click through */ }
            .padding(Spacing.lg)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Header with close button
            ModalHeader(onDismiss = onDismiss)

            // Horizontal scrolling months
            MonthlyActivitySection(monthlyActivity = monthlyActivity)

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // Overall stats section
            OverallStatsSection(stats = stats)
        }
    }
}

@Composable
private fun ModalHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Activity History",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(SurfaceElevated)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun MonthlyActivitySection(monthlyActivity: List<MonthActivity>) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        contentPadding = PaddingValues(horizontal = Spacing.xs),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(monthlyActivity) { month ->
            MonthCard(monthActivity = month)
        }
    }
}

@Composable
private fun MonthCard(monthActivity: MonthActivity) {
    val monthName = monthActivity.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val year = monthActivity.yearMonth.year

    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(CornerRadius.lg))
            .background(SurfaceElevated)
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Month name
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        // Mini activity grid
        MiniActivityGrid(days = monthActivity.days)

        // Total interactions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = monthActivity.totalInteractions.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = ActivityHigh,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "interactions",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun MiniActivityGrid(days: List<com.example.socialapp.data.DayActivity>) {
    val rows = 5
    val cols = 7

    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                for (col in 0 until cols) {
                    val index = row * cols + col
                    if (index < days.size) {
                        MiniActivityDot(interactionCount = days[index].interactionCount)
                    } else {
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniActivityDot(interactionCount: Int) {
    val color = when (interactionCount) {
        0 -> BackgroundTertiary
        1, 2 -> ActivityLow
        3, 4 -> ActivityMedium
        in 5..7 -> ActivityHigh
        else -> ActivityMax
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}

@Composable
private fun OverallStatsSection(stats: SocialStats) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        Text(
            text = "Streak Progress",
            style = MaterialTheme.typography.titleMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )

        // Current Streak and Longest Streak (reverting to previous layout)
        StreakProgressBar(
            currentStreak = stats.streakCount,
            longestStreak = stats.longestStreak
        )

        Spacer(modifier = Modifier.height(Spacing.md)) // Add some space

        // Other stats - not in boxes, simple text rows
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // Total Active Days
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Active Days",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Text(
                    text = stats.activeDays.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
            }

            // Most Active Day
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Most Active Day",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Text(
                    text = "${stats.mostActiveDay?.interactionCount ?: 0} interactions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
private fun StreakProgressBar(
    currentStreak: Int,
    longestStreak: Int
) {
    val progress = if (longestStreak > 0) {
        (currentStreak.toFloat() / longestStreak.toFloat()).coerceIn(0f, 1f)
    } else 0f

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Current Streak row with progress bar
        StreakRow(
            label = "Current",
            value = "${currentStreak}d",
            progress = progress,
            color = StreakPrimary
        )

        // Longest Streak - just text, no bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Longest",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = "${longestStreak}d",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun StreakRow(
    label: String,
    value: String,
    progress: Float,
    color: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Label and value row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }

        // Thin progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(BackgroundTertiary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
        }
    }
}


@Composable
private fun StatItem(
    value: String,
    label: String,
    suffix: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(CornerRadius.md))
            .background(SurfaceElevated)
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            if (suffix.isNotEmpty()) {
                Text(
                    text = suffix,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextTertiary
        )
    }
}

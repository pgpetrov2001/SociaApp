package com.example.socialapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
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
import kotlin.math.roundToInt

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

    Column(
        modifier = Modifier.width(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Month name
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleSmall,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        // Mini activity grid
        MiniActivityGrid(days = monthActivity.days)

        // Total interactions
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = monthActivity.totalInteractions.toString(),
                fontSize = 18.sp,
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
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        Spacer(modifier = Modifier.size(10.dp))
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
            .size(10.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}

@Composable
private fun OverallStatsSection(stats: SocialStats) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        // Statistics section - moved to top
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Total Active Days
            StatRow(
                label = "Total Active Days",
                value = stats.activeDays.toString()
            )

            // Most Active Day
            StatRow(
                label = "Most Active Day",
                value = "${stats.mostActiveDay?.interactionCount ?: 0} interactions"
            )
        }

        // Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(TextMuted.copy(alpha = 0.2f))
        )

        // Streak Progress - moved to bottom
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Text(
                text = "Streak Progress",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )

            // Current Streak and Longest Streak
            StreakProgressBar(
                currentStreak = stats.streakCount,
                longestStreak = stats.longestStreak
            )
        }
    }
}

@Composable
private fun StatRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )
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

        // Longest Streak - larger and bolder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Longest",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${longestStreak}d",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
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

@Composable
fun StatisticsBottomSheet(
    stats: SocialStats,
    monthlyActivity: List<MonthActivity>,
    onDismiss: () -> Unit
) {
    var currentPage by remember { mutableStateOf(0) }
    val pageCount = 3

    // Calculate statistics
    val currentMonth = monthlyActivity.getOrNull(0)
    val lastMonth = monthlyActivity.getOrNull(1)
    val currentMonthTotal = currentMonth?.totalInteractions ?: 0
    val lastMonthTotal = lastMonth?.totalInteractions ?: 0
    val averageInteractions = if (monthlyActivity.isNotEmpty()) {
        monthlyActivity.map { it.totalInteractions }.average()
    } else 0.0
    val bestMonth = monthlyActivity.maxByOrNull { it.totalInteractions }
    val bestMonthTotal = bestMonth?.totalInteractions ?: 0
    val maxMonthlyValue = monthlyActivity.maxOfOrNull { it.totalInteractions } ?: 1

    // Calculate percentages
    val vsLastMonth = if (lastMonthTotal > 0) {
        ((currentMonthTotal - lastMonthTotal).toFloat() / lastMonthTotal * 100).roundToInt()
    } else 0
    val vsAverage = if (averageInteractions > 0) {
        ((currentMonthTotal - averageInteractions) / averageInteractions * 100).roundToInt()
    } else 0
    val vsBest = if (bestMonthTotal > 0) {
        ((currentMonthTotal - bestMonthTotal).toFloat() / bestMonthTotal * 100).roundToInt()
    } else 0

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Background overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismiss() }
        )

        // Full height sheet
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(SurfaceElevatedHigher)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { /* Prevent dismiss */ }
        ) {
            // Handle bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TextMuted.copy(alpha = 0.3f))
                )
            }

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceElevated)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Page tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                listOf("Overview", "Monthly", "Weekly").forEachIndexed { index, title ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (currentPage == index) ActivityHigh else SurfaceElevated)
                            .clickable { currentPage = index }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (currentPage == index) Color.White else TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Page content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.lg)
            ) {
                when (currentPage) {
                    0 -> OverviewPage(
                        currentMonthTotal = currentMonthTotal,
                        vsLastMonth = vsLastMonth,
                        lastMonthTotal = lastMonthTotal,
                        vsAverage = vsAverage,
                        averageInteractions = averageInteractions.roundToInt(),
                        vsBest = vsBest,
                        bestMonthTotal = bestMonthTotal
                    )
                    1 -> MonthlyChartPage(
                        monthlyActivity = monthlyActivity,
                        maxValue = maxMonthlyValue
                    )
                    2 -> WeeklyChartPage(stats = stats)
                }
            }
        }
    }
}

@Composable
private fun OverviewPage(
    currentMonthTotal: Int,
    vsLastMonth: Int,
    lastMonthTotal: Int,
    vsAverage: Int,
    averageInteractions: Int,
    vsBest: Int,
    bestMonthTotal: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Big number display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceElevated)
                .padding(Spacing.lg),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentMonthTotal.toString(),
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = ActivityHigh
                )
                Text(
                    text = "interactions this month",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        // Comparison cards
        ComparisonCard(
            title = "vs Last Month",
            percentage = vsLastMonth,
            subtitle = "Last month: $lastMonthTotal"
        )
        ComparisonCard(
            title = "vs Your Average",
            percentage = vsAverage,
            subtitle = "Average: $averageInteractions"
        )
        ComparisonCard(
            title = "vs Your Best",
            percentage = vsBest,
            subtitle = "Best: $bestMonthTotal"
        )
    }
}

@Composable
private fun MonthlyChartPage(
    monthlyActivity: List<MonthActivity>,
    maxValue: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        Text(
            text = "Monthly Trend",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        // Bar chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceElevated)
                .padding(Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                monthlyActivity.reversed().take(6).forEach { month ->
                    val heightFraction = if (maxValue > 0) {
                        month.totalInteractions.toFloat() / maxValue
                    } else 0f
                    val monthName = month.yearMonth.month.getDisplayName(
                        TextStyle.SHORT, Locale.getDefault()
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Value on top
                        Text(
                            text = month.totalInteractions.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        // Bar
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .fillMaxHeight(heightFraction.coerceAtLeast(0.05f))
                                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                .background(
                                    if (month == monthlyActivity.firstOrNull()) ActivityHigh
                                    else ActivityMedium
                                )
                        )
                        // Month label
                        Text(
                            text = monthName,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Monthly breakdown list
        Text(
            text = "Monthly Breakdown",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            monthlyActivity.take(4).forEach { month ->
                val monthName = month.yearMonth.month.getDisplayName(
                    TextStyle.FULL, Locale.getDefault()
                )
                val progress = if (maxValue > 0) {
                    month.totalInteractions.toFloat() / maxValue
                } else 0f

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceElevated)
                        .padding(Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = monthName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        modifier = Modifier.width(80.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(BackgroundTertiary)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progress)
                                .clip(RoundedCornerShape(4.dp))
                                .background(ActivityHigh)
                        )
                    }
                    Text(
                        text = month.totalInteractions.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = Spacing.sm)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklyChartPage(stats: SocialStats) {
    val weeklyData = remember {
        listOf(
            "Mon" to (3..8).random(),
            "Tue" to (2..7).random(),
            "Wed" to (4..9).random(),
            "Thu" to (1..6).random(),
            "Fri" to (5..10).random(),
            "Sat" to (6..12).random(),
            "Sun" to (4..8).random()
        )
    }
    val maxWeekly = weeklyData.maxOf { it.second }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        // Weekly bar chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceElevated)
                .padding(Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                weeklyData.forEachIndexed { index, (day, value) ->
                    val heightFraction = value.toFloat() / maxWeekly
                    val isToday = index == java.time.LocalDate.now().dayOfWeek.value - 1

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = value.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isToday) ActivityHigh else TextSecondary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .fillMaxHeight(heightFraction.coerceAtLeast(0.1f))
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isToday) ActivityHigh else ActivityMedium.copy(alpha = 0.6f))
                        )
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isToday) ActivityHigh else TextMuted,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Stats summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            StatSummaryCard(
                title = "Weekly Total",
                value = weeklyData.sumOf { it.second }.toString(),
                modifier = Modifier.weight(1f)
            )
            StatSummaryCard(
                title = "Daily Avg",
                value = String.format("%.1f", weeklyData.sumOf { it.second } / 7.0),
                modifier = Modifier.weight(1f)
            )
            StatSummaryCard(
                title = "Best Day",
                value = weeklyData.maxOf { it.second }.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        // Activity heatmap preview
        Text(
            text = "Activity Pattern",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceElevated)
                .padding(Spacing.md)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(4) { week ->
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(7) { day ->
                            val intensity = (0..4).random()
                            val color = when (intensity) {
                                0 -> BackgroundTertiary
                                1 -> ActivityLow
                                2 -> ActivityMedium
                                3 -> ActivityHigh
                                else -> ActivityMax
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(color)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatSummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceElevated)
            .padding(Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ActivityHigh
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
    }
}

@Composable
private fun ComparisonCard(
    title: String,
    percentage: Int,
    subtitle: String
) {
    val percentageColor = when {
        percentage > 0 -> ActivityHigh
        percentage < 0 -> Color(0xFFFF6B6B)
        else -> TextSecondary
    }
    val percentageText = when {
        percentage > 0 -> "+$percentage%"
        else -> "$percentage%"
    }
    val statusText = when {
        percentage > 0 -> "better"
        percentage < 0 -> "less"
        else -> "same"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceElevated)
            .padding(Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = percentageText,
                fontSize = 18.sp,
                color = percentageColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

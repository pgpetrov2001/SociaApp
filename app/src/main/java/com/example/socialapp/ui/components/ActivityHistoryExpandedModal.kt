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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

        // Sheet
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(BackgroundPrimary)
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
                        .width(36.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TextMuted.copy(alpha = 0.4f))
                )
            }

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    fontSize = 24.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(TextMuted.copy(alpha = 0.2f))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Minimal tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Overview", "Monthly", "Weekly").forEachIndexed { index, title ->
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = if (currentPage == index) ActivityHigh else TextMuted,
                        fontWeight = if (currentPage == index) FontWeight.SemiBold else FontWeight.Normal,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (currentPage == index) ActivityHigh.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable { currentPage = index }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Page content with scroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 32.dp)
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Big number - no box
        Text(
            text = currentMonthTotal.toString(),
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = ActivityHigh
        )
        Text(
            text = "interactions this month",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Comparison rows - minimal, no boxes
        ComparisonRow(
            title = "vs Last Month",
            percentage = vsLastMonth,
            subtitle = "$lastMonthTotal"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ComparisonRow(
            title = "vs Average",
            percentage = vsAverage,
            subtitle = "$averageInteractions"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ComparisonRow(
            title = "vs Best",
            percentage = vsBest,
            subtitle = "$bestMonthTotal"
        )
    }
}

@Composable
private fun MonthlyChartPage(
    monthlyActivity: List<MonthActivity>,
    maxValue: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Monthly Trend",
            fontSize = 16.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Bar chart - no box background
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
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
                val isCurrent = month == monthlyActivity.firstOrNull()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = month.totalInteractions.toString(),
                        fontSize = 12.sp,
                        color = if (isCurrent) ActivityHigh else TextMuted,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .fillMaxHeight(heightFraction.coerceAtLeast(0.08f))
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isCurrent) ActivityHigh else ActivityMedium.copy(alpha = 0.5f))
                    )
                    Text(
                        text = monthName,
                        fontSize = 11.sp,
                        color = if (isCurrent) TextPrimary else TextMuted,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Monthly breakdown - minimal rows
        Text(
            text = "Breakdown",
            fontSize = 16.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        monthlyActivity.take(4).forEach { month ->
            val monthName = month.yearMonth.month.getDisplayName(
                TextStyle.SHORT, Locale.getDefault()
            )
            val progress = if (maxValue > 0) {
                month.totalInteractions.toFloat() / maxValue
            } else 0f

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = monthName,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.width(48.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(BackgroundTertiary)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .clip(RoundedCornerShape(3.dp))
                            .background(ActivityHigh)
                    )
                }
                Text(
                    text = month.totalInteractions.toString(),
                    fontSize = 14.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun WeeklyChartPage(stats: SocialStats) {
    val weeklyData = remember {
        listOf(
            "M" to (3..8).random(),
            "T" to (2..7).random(),
            "W" to (4..9).random(),
            "T" to (1..6).random(),
            "F" to (5..10).random(),
            "S" to (6..12).random(),
            "S" to (4..8).random()
        )
    }
    val maxWeekly = weeklyData.maxOf { it.second }
    val weeklyTotal = weeklyData.sumOf { it.second }
    val dailyAvg = weeklyTotal / 7.0

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "This Week",
            fontSize = 16.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Weekly bar chart - no box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
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
                        fontSize = 11.sp,
                        color = if (isToday) ActivityHigh else TextMuted,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .fillMaxHeight(heightFraction.coerceAtLeast(0.1f))
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isToday) ActivityHigh else ActivityMedium.copy(alpha = 0.4f))
                    )
                    Text(
                        text = day,
                        fontSize = 12.sp,
                        color = if (isToday) ActivityHigh else TextMuted,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats summary - minimal, no boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatValue(
                value = weeklyTotal.toString(),
                label = "Total"
            )
            StatValue(
                value = String.format("%.1f", dailyAvg),
                label = "Daily avg"
            )
            StatValue(
                value = weeklyData.maxOf { it.second }.toString(),
                label = "Best"
            )
        }
    }
}

@Composable
private fun StatValue(
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = ActivityHigh
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextMuted
        )
    }
}

@Composable
private fun ComparisonRow(
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontSize = 15.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = TextMuted
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
                fontSize = 13.sp,
                color = TextMuted
            )
        }
    }
}

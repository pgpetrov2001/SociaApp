package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialapp.data.SocialStats
import com.example.socialapp.data.generateSampleData
import com.example.socialapp.ui.components.*
import com.example.socialapp.ui.theme.*
import com.example.socialapp.ui.viewmodel.SocialTrackerViewModel
import kotlinx.coroutines.launch

/**
 * Main Social Tracker screen with iOS-style widget layout
 * Integrated with ViewModel for state management
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialTrackerScreen(
    modifier: Modifier = Modifier,
    stats: SocialStats = remember { generateSampleData() },
    activeDays: Int = 125,
    currentStreak: Int = 7,
    viewModel: SocialTrackerViewModel = viewModel()
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Modal states
    var showActivityHistoryModal by remember { mutableStateOf(false) }
    var showSettingsModal by remember { mutableStateOf(false) }
    var showAddInteractionModal by remember { mutableStateOf(false) }
    var showNotesHistoryModal by remember { mutableStateOf(false) }
    var showSubscriptionModal by remember { mutableStateOf(false) }
    var showStatsSheet by remember { mutableStateOf(false) }
    var showHintsSheet by remember { mutableStateOf(false) }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Determine if any modal is visible
    val isAnyModalVisible = showActivityHistoryModal || showSettingsModal ||
        showAddInteractionModal || showNotesHistoryModal || showSubscriptionModal ||
        showStatsSheet || showHintsSheet

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundPrimary)
                .then(
                    if (isAnyModalVisible) Modifier.blur(8.dp)
                    else Modifier
                ),
            containerColor = BackgroundPrimary,
            topBar = {
                SocialTrackerTopBar(
                    currentStreak = currentStreak,
                    onSettingsClick = { showSettingsModal = true }
                )
            },
            floatingActionButton = {
                SocialTrackerFAB(
                    onClick = { showAddInteractionModal = true }
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.md)
                    .padding(bottom = 80.dp), // Extra padding for FAB
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                // Row 1: Today widget and Lifetime widget side by side
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    // Widget 1: Today's conversation count
                    TodayWidget(
                        todayCount = uiState.todayCount,
                        dailyGoal = uiState.dailyQuota,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    // Widget 2: Lifetime total conversations
                    LifetimeWidget(
                        totalCount = uiState.totalCount,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }

                // Widget 3: Activity History (full width, same height as widgets 1 & 2)
                ActivityHistoryWidget(
                    activeDays = activeDays,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    onClick = { showActivityHistoryModal = true }
                )

                // Widget 4: Notes History (full width, half height)
                NotesHistoryWidget(
                    notesCount = uiState.interactions.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    onClick = { showNotesHistoryModal = true }
                )

                // Extra spacing at bottom for FAB and bottom bar
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // Activity History Expanded Modal
        ActivityHistoryExpandedModal(
            isVisible = showActivityHistoryModal,
            stats = stats,
            onDismiss = { showActivityHistoryModal = false }
        )

        // Settings Modal
        SettingsModal(
            isVisible = showSettingsModal,
            currentQuota = uiState.dailyQuota,
            onQuotaChange = { newQuota -> viewModel.updateDailyQuota(newQuota) },
            onDismiss = { showSettingsModal = false },
            onViewPlanClick = {
                showSettingsModal = false
                showSubscriptionModal = true
            }
        )

        // Subscription Modal
        SubscriptionModal(
            isVisible = showSubscriptionModal,
            currentPlan = "Weekly",
            planPrice = "9.99 USD/week",
            onDismiss = { showSubscriptionModal = false },
            onCancelSubscription = { reason, comment ->
                // Handle cancellation - in a real app, this would call an API
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Subscription cancelled. We're sorry to see you go!",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )

        // Add Interaction Modal
        AddInteractionModal(
            isVisible = showAddInteractionModal,
            onSave = { qualityRating, noteText ->
                viewModel.addInteraction(qualityRating, noteText)

                // Show celebratory snackbar
                val todayCount = uiState.todayCount + 1
                val quota = uiState.dailyQuota
                val message = getEncouragingMessage(todayCount, quota)

                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }

                showAddInteractionModal = false
            },
            onDismiss = { showAddInteractionModal = false }
        )

        // Notes History Modal
        NotesHistoryModal(
            isVisible = showNotesHistoryModal,
            interactions = uiState.interactions,
            onDismiss = { showNotesHistoryModal = false }
        )

        // Statistics Bottom Sheet
        if (showStatsSheet) {
            StatisticsBottomSheet(
                stats = stats,
                monthlyActivity = stats.getMonthlyActivity(6),
                onDismiss = { showStatsSheet = false }
            )
        }

        // Hints Bottom Sheet
        if (showHintsSheet) {
            HintsBottomSheet(
                onDismiss = { showHintsSheet = false }
            )
        }

        // Bottom Navigation Bar
        BottomNavBar(
            onHintsClick = { showHintsSheet = true },
            onStatsClick = { showStatsSheet = true },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Snackbar at top of screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        ) {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    CustomSnackbar(data)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SocialTrackerTopBar(
    currentStreak: Int,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Social Tracker",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            // Streak badge
            Box(
                modifier = Modifier
                    .padding(end = Spacing.sm)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceElevated)
                    .padding(horizontal = Spacing.md),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔥",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = currentStreak.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = StreakPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Settings gear icon
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceElevated)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundPrimary
        ),
        modifier = Modifier.padding(horizontal = Spacing.xs)
    )
}

@Composable
private fun SocialTrackerFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        containerColor = SurfaceElevated,
        contentColor = TextPrimary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add conversation",
            modifier = Modifier.size(32.dp)
        )
    }
}

/**
 * Custom styled snackbar matching iOS design
 * Appears at top with grey background
 * Swipe up to dismiss
 */
@Composable
private fun CustomSnackbar(data: SnackbarData) {
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offsetY.toInt()) }
            .padding(horizontal = Spacing.md, vertical = Spacing.xs)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(CornerRadius.lg)
            )
            .clip(RoundedCornerShape(CornerRadius.lg))
            .background(SurfaceElevated)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragEnd = {
                        if (offsetY < -100f) {
                            // Swiped up enough, dismiss
                            data.dismiss()
                        } else {
                            // Not enough, reset position
                            offsetY = 0f
                        }
                    },
                    onVerticalDrag = { _, dragAmount ->
                        // Only allow upward swipes
                        val newOffset = offsetY + dragAmount
                        if (newOffset <= 0f) {
                            offsetY = newOffset
                        }
                    }
                )
            }
            .padding(horizontal = Spacing.md, vertical = Spacing.md)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🎉",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = data.visuals.message,
                style = MaterialTheme.typography.bodyLarge,
                color = androidx.compose.ui.graphics.Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Generate encouraging message based on quota progress
 */
private fun getEncouragingMessage(currentCount: Int, quota: Int): String {
    val progress = if (quota > 0) (currentCount.toFloat() / quota * 100).toInt() else 0

    return when {
        currentCount >= quota -> "🔥 Quota complete! You're crushing it!"
        progress >= 75 -> "$currentCount out of $quota complete! Almost there!"
        progress >= 50 -> "$currentCount out of $quota complete! Halfway done!"
        progress >= 25 -> "$currentCount out of $quota complete! Keep it up!"
        else -> "$currentCount out of $quota complete! Great start!"
    }
}

/**
 * Minimal bottom bar - iOS style with small icon buttons
 */
@Composable
private fun BottomNavBar(
    onHintsClick: () -> Unit,
    onStatsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 4.dp)
    ) {
        // Left button - Hints (grid icon)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceElevated)
                .clickable { onHintsClick() },
            contentAlignment = Alignment.Center
        ) {
            GridIcon(color = TextSecondary)
        }

        // Center - Home indicator line
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp)
                .width(134.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(2.5.dp))
                .background(TextMuted.copy(alpha = 0.5f))
        )

        // Right button - Stats (bar chart icon)
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceElevated)
                .clickable { onStatsClick() },
            contentAlignment = Alignment.Center
        ) {
            BarChartIcon(color = TextSecondary)
        }
    }
}

@Composable
private fun GridIcon(color: androidx.compose.ui.graphics.Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        repeat(2) {
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(color)
                    )
                }
            }
        }
    }
}

@Composable
private fun BarChartIcon(color: androidx.compose.ui.graphics.Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(color)
        )
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(14.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(color)
        )
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(color)
        )
    }
}

/**
 * Hints bottom sheet with conversation openers
 */
@Composable
private fun HintsBottomSheet(
    onDismiss: () -> Unit
) {
    val openers = listOf(
        "💬" to "Hey, I noticed your [item/accessory] - where did you get it?",
        "😊" to "You look like you're having a good day! What's the secret?",
        "🎵" to "What song would you say describes your vibe right now?",
        "☕" to "I'm grabbing coffee - any recommendations around here?",
        "📚" to "Reading anything good lately? I need suggestions!",
        "🌟" to "I have to say, you have great energy. What do you do?",
        "🎬" to "Seen any good movies or shows lately?",
        "✈️" to "You look like someone who's been on adventures - what's your favorite place?"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Background overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismiss() }
        )

        // Bottom sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(SurfaceElevatedHigher)
                .padding(Spacing.lg)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Handle bar
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(TextMuted.copy(alpha = 0.3f))
            )

            // Title
            Text(
                text = "Conversation Starters",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tap to copy, then go start a conversation!",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            // Random opener highlight
            val randomOpener = remember { openers.random() }
            OpenerCard(
                emoji = randomOpener.first,
                text = randomOpener.second,
                isHighlighted = true
            )

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // More openers
            Text(
                text = "More ideas:",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )

            openers.filter { it != randomOpener }.take(3).forEach { (emoji, text) ->
                OpenerCard(emoji = emoji, text = text, isHighlighted = false)
            }

            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }
}

@Composable
private fun OpenerCard(
    emoji: String,
    text: String,
    isHighlighted: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isHighlighted) ActivityHigh.copy(alpha = 0.15f) else SurfaceElevated)
            .clickable { /* TODO: Copy to clipboard */ }
            .padding(Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isHighlighted) TextPrimary else TextSecondary,
            fontWeight = if (isHighlighted) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun SocialTrackerScreenPreview() {
    SocialAppTheme(darkTheme = true, dynamicColor = false) {
        SocialTrackerScreen()
    }
}

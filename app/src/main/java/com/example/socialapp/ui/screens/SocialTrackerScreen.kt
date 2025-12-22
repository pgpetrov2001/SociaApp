package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.components.*
import com.example.socialapp.ui.theme.*

/**
 * Main Social Tracker screen with iOS-style widget layout
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialTrackerScreen(
    modifier: Modifier = Modifier,
    todayCount: Int = 7,
    dailyGoal: Int = 7,
    lifetimeTotal: Int = 0,
    activeDays: Int = 125,
    notesCount: Int = 251,
    currentStreak: Int = 7
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary),
        containerColor = BackgroundPrimary,
        topBar = {
            SocialTrackerTopBar(currentStreak = currentStreak)
        },
        floatingActionButton = {
            SocialTrackerFAB()
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
                    todayCount = todayCount,
                    dailyGoal = dailyGoal,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                // Widget 2: Lifetime total conversations
                LifetimeWidget(
                    totalCount = lifetimeTotal,
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
                    .height(220.dp)
            )

            // Widget 4: Notes History (full width, half height)
            NotesHistoryWidget(
                notesCount = notesCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            )

            // Extra spacing at bottom for FAB
            Spacer(modifier = Modifier.height(Spacing.lg))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SocialTrackerTopBar(currentStreak: Int) {
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
                onClick = { /* Handle settings click */ },
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
private fun SocialTrackerFAB() {
    FloatingActionButton(
        onClick = { /* Handle add conversation */ },
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

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun SocialTrackerScreenPreview() {
    SocialAppTheme(darkTheme = true, dynamicColor = false) {
        SocialTrackerScreen()
    }
}

package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OnboardingOption(
    val emoji: String,
    val text: String
)

data class OnboardingStepData(
    val title: String,
    val options: List<OnboardingOption>,
    val step: Int
)

private val DarkNavyBackground = Color(0xFF0D1B2A)
private val CardBackground = Color(0xFF1B2838)
private val BackButtonBackground = Color(0xFF1B2838)
private val ProgressActiveColor = Color(0xFF4A90D9)
private val ProgressInactiveColor = Color(0xFF2A3A4A)
private val ContinueButtonDisabled = Color(0xFF3A3A3C)
private val ContinueTextDisabled = Color(0xFF8E8E93)

val onboardingSteps = listOf(
    OnboardingStepData(
        title = "What's on your mind?",
        options = listOf(
            OnboardingOption("😌", "Elevate mood"),
            OnboardingOption("😰", "Reduce stress & anxiety"),
            OnboardingOption("😴", "Improve sleep"),
            OnboardingOption("🚀", "Increase productivity"),
            OnboardingOption("🧘", "Find inner peace"),
            OnboardingOption("💪", "Build confidence")
        ),
        step = 1
    ),
    OnboardingStepData(
        title = "How old are you?",
        options = listOf(
            OnboardingOption("👶", "Under 18"),
            OnboardingOption("🧑", "18-24"),
            OnboardingOption("👨", "25-34"),
            OnboardingOption("🧔", "35-44"),
            OnboardingOption("👴", "45-54"),
            OnboardingOption("👵", "55+")
        ),
        step = 2
    ),
    OnboardingStepData(
        title = "What's one activity that never fails to lift your mood?",
        options = listOf(
            OnboardingOption("🎥", "Watching something funny"),
            OnboardingOption("💞", "Spending time with loved ones"),
            OnboardingOption("🍳🎨", "Cooking or creating something"),
            OnboardingOption("🛏️", "Taking a nap"),
            OnboardingOption("🌞", "Getting out in the sun"),
            OnboardingOption("🐶", "Spending time with my pet")
        ),
        step = 3
    ),
    OnboardingStepData(
        title = "How do you usually handle stress?",
        options = listOf(
            OnboardingOption("🎧", "Listen to music"),
            OnboardingOption("🏃", "Exercise or go for a walk"),
            OnboardingOption("📱", "Scroll through social media"),
            OnboardingOption("🗣️", "Talk to someone"),
            OnboardingOption("🍫", "Eat comfort food"),
            OnboardingOption("😤", "I don't handle it well")
        ),
        step = 4
    ),
    OnboardingStepData(
        title = "What time of day do you feel most energized?",
        options = listOf(
            OnboardingOption("🌅", "Early morning"),
            OnboardingOption("☀️", "Late morning"),
            OnboardingOption("🌤️", "Afternoon"),
            OnboardingOption("🌆", "Evening"),
            OnboardingOption("🌙", "Late night"),
            OnboardingOption("🤷", "It varies")
        ),
        step = 5
    ),
    OnboardingStepData(
        title = "What's your main goal with this app?",
        options = listOf(
            OnboardingOption("📈", "Track my progress"),
            OnboardingOption("🎯", "Build better habits"),
            OnboardingOption("⚡", "Stay consistent"),
            OnboardingOption("🏆", "Achieve goals faster"),
            OnboardingOption("🧠", "Understand myself better"),
            OnboardingOption("✨", "Just exploring")
        ),
        step = 6
    )
)

@Composable
fun OnboardingScreen(
    step: Int, // 1 to 6
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit,
    onSkip: () -> Unit
) {
    val stepData = onboardingSteps[step - 1]
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val totalSteps = 6

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Top bar with back button and progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackButtonBackground)
                        .clickable { onNavigateBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Progress bar
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(totalSteps) { index ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    if (index < step) ProgressActiveColor
                                    else ProgressInactiveColor
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Question title
            Text(
                text = stepData.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Options list - scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                stepData.options.forEach { option ->
                    val isSelected = selectedOption == option.text

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) CardBackground.copy(alpha = 0.9f)
                                else CardBackground
                            )
                            .clickable {
                                selectedOption = option.text
                            }
                            .padding(horizontal = 20.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option.emoji,
                            fontSize = 24.sp
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = option.text,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Continue button
            val buttonInteractionSource = remember { MutableInteractionSource() }
            val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()
            val isEnabled = selectedOption != null

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        when {
                            !isEnabled -> ContinueButtonDisabled
                            isButtonPressed -> Color(0xFFD0D0D0)
                            else -> Color(0xFFAEAEB2)
                        }
                    )
                    .then(
                        if (isEnabled) {
                            Modifier.clickable(
                                interactionSource = buttonInteractionSource,
                                indication = null
                            ) { onNavigateNext() }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isEnabled) Color.Black else ContinueTextDisabled
                )
            }

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

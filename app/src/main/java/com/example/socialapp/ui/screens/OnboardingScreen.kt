package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

data class OnboardingStepData(
    val title: String,
    val options: List<String>,
    val step: Int // 1, 2, or 3
)

val onboardingSteps = listOf(
    OnboardingStepData(
        title = "What's on your mind?",
        options = listOf(
            "Elevate mood",
            "Reduce stress & anxiety",
            "Improve sleep",
            "Increase productivity"
        ),
        step = 1
    ),
    OnboardingStepData(
        title = "How old are you?",
        options = listOf(
            "Under 18",
            "18-24",
            "25-34",
            "35-44"
        ),
        step = 2
    ),
    OnboardingStepData(
        title = "What's your main goal?",
        options = listOf(
            "Build better habits",
            "Track my progress",
            "Stay consistent",
            "Achieve goals faster"
        ),
        step = 3
    )
)

@Composable
fun OnboardingScreen(
    step: Int, // 1, 2, or 3
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit,
    onSkip: () -> Unit
) {
    val stepData = onboardingSteps[step - 1]
    var selectedOptions by remember { mutableStateOf(setOf<String>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(24.dp)
    ) {
        // Skip button
        Text(
            text = "Skip",
            color = Color(0xFF8E8E93),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { onSkip() }
                .padding(8.dp)
        )

        // Back button (only for steps 2 and 3)
        if (step > 1) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stepData.title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "Your answers will help shape the app\naround your needs.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8E8E93),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Options
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                stepData.options.forEach { option ->
                    val isSelected = option in selectedOptions

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                if (isSelected) Color(0xFF2C2C2E)
                                else Color(0xFF1C1C1E)
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) Color(0xFF3A3A3C)
                                else Color(0xFF2C2C2E),
                                shape = RoundedCornerShape(30.dp)
                            )
                            .clickable {
                                selectedOptions = if (isSelected) {
                                    selectedOptions - option
                                } else {
                                    selectedOptions + option
                                }
                            }
                            .padding(horizontal = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Disclaimer text
            Text(
                text = "Your selections won't limit access to any\nfeatures.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6E6E73),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Continue button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFAEAEB2))
                    .clickable { onNavigateNext() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Progress dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (index == step - 1) Color.White
                                else Color(0xFF3A3A3C),
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

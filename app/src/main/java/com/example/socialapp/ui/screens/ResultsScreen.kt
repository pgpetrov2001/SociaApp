package com.example.socialapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Colors matching OnboardingScreen
private val DarkNavyBackground = Color(0xFF0D1B2A)
private val CardBackground = Color(0xFF1B2838)
private val ProgressActiveColor = Color(0xFF4A90D9)
private val ProgressInactiveColor = Color(0xFF4A5568)

data class StatItem(
    val percentage: String,
    val description: String,
    val icon: String
)

@Composable
fun ResultsScreen(
    onNavigateNext: () -> Unit
) {
    val stats = listOf(
        StatItem(
            percentage = "86%",
            description = "Overcome fear of talking to girls",
            icon = "💬"
        ),
        StatItem(
            percentage = "92%",
            description = "Build lasting discipline",
            icon = "🎯"
        ),
        StatItem(
            percentage = "94%",
            description = "Transform their social life",
            icon = "🚀"
        )
    )

    // Animation state for stats appearing
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    val continueButtonInteraction = remember { MutableInteractionSource() }
    val isContinuePressed by continueButtonInteraction.collectIsPressedAsState()

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

            // Progress bar - full progress since we're past all questions
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(ProgressInactiveColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(1f)
                        .clip(RoundedCornerShape(3.dp))
                        .background(ProgressActiveColor)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Main heading
            Text(
                text = "Your Journey\nStarts Here",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subheading
            Text(
                text = "Join thousands who've transformed their lives",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8E8E93),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Stats section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                stats.forEachIndexed { index, stat ->
                    AnimatedStatCard(
                        stat = stat,
                        isVisible = isVisible,
                        delay = index * 150L
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Trust indicator
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "⭐️ 4.9/5",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = " • ",
                    fontSize = 16.sp,
                    color = Color(0xFF6E6E73)
                )
                Text(
                    text = "50,000+ reviews",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8E8E93)
                )
            }

            // Continue button - matching OnboardingScreen style
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isContinuePressed) Color(0xFFD0D0D0)
                        else Color(0xFFAEAEB2)
                    )
                    .clickable(
                        interactionSource = continueButtonInteraction,
                        indication = null
                    ) { onNavigateNext() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun AnimatedStatCard(
    stat: StatItem,
    isVisible: Boolean,
    delay: Long
) {
    var animationPlayed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(delay)
            animationPlayed = true
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Text(
            text = stat.icon,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Text content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stat.percentage,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stat.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFAEAEB2),
                lineHeight = 18.sp
            )
        }
    }
}

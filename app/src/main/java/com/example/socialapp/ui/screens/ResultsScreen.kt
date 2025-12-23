package com.example.socialapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

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
            .background(Color(0xFF0A0A0A))
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main heading
                Text(
                    text = "Your Journey\nStarts Here",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 42.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // Subheading
                Text(
                    text = "Join thousands who've transformed their lives",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8E8E93),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )

                // Stats section
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
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
            }

            // Bottom section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trust indicator - compact version
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
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

                // Continue button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = if (isContinuePressed) {
                                    listOf(
                                        Color(0xFFFFFFFF),
                                        Color(0xFFE5E5E7)
                                    )
                                } else {
                                    listOf(
                                        Color(0xFFE5E5E7),
                                        Color(0xFFFFFFFF)
                                    )
                                }
                            )
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
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1C1C1E),
                        Color(0xFF0F0F10)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF2C2C2E),
                                Color(0xFF1C1C1E)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stat.icon,
                    fontSize = 24.sp
                )
            }

            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stat.percentage,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 2.dp)
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
}

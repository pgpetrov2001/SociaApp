package com.example.socialapp.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkNavyBackground = Color(0xFF0D1B2A)
private val CardBackground = Color(0xFF1B2838)
private val AccentBlue = Color(0xFF4A90D9)

data class BenefitItem(
    val emoji: String,
    val title: String,
    val description: String
)

@Composable
fun BenefitsScreen(
    onNavigateNext: () -> Unit
) {
    val benefits = listOf(
        BenefitItem(
            emoji = "🔔",
            title = "Daily Reminders",
            description = "Never forget to practice. We'll remind you at the perfect time to take action."
        ),
        BenefitItem(
            emoji = "💪",
            title = "Constant Motivation",
            description = "Get personalized encouragement that keeps you moving forward, even on tough days."
        ),
        BenefitItem(
            emoji = "🚫",
            title = "No More Excuses",
            description = "Our system holds you accountable. Skip the excuses and start building real confidence."
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Header
            Text(
                text = "Why This Works",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Everything you need to transform your social life",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Benefits list
            benefits.forEach { benefit ->
                BenefitCard(benefit = benefit)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Continue button
            val buttonInteractionSource = remember { MutableInteractionSource() }
            val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isButtonPressed) Color(0xFF3A7BC8)
                        else AccentBlue
                    )
                    .clickable(
                        interactionSource = buttonInteractionSource,
                        indication = null
                    ) { onNavigateNext() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun BenefitCard(benefit: BenefitItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .padding(20.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = benefit.emoji,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = benefit.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = benefit.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )
        }
    }
}

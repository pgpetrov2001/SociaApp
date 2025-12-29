package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
private val AccentBlue = Color(0xFF4A90D9)
private val SuccessGreen = Color(0xFF39D98A)

@Composable
fun NotificationPermissionScreen(
    onEnableNotifications: () -> Unit,
    onSkip: () -> Unit
) {
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
            Spacer(modifier = Modifier.weight(0.15f))

            // Bell icon in circle
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(AccentBlue.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔔",
                    fontSize = 56.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "Turn On Notifications",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "Enable notifications so we can remind you to practice and send you motivation when you need it most.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Benefits mini-list
            NotificationBenefit(emoji = "⏰", text = "Daily reminders to take action")
            Spacer(modifier = Modifier.height(12.dp))
            NotificationBenefit(emoji = "💬", text = "Motivational messages when you need them")
            Spacer(modifier = Modifier.height(12.dp))
            NotificationBenefit(emoji = "🎯", text = "Track your progress and stay consistent")

            Spacer(modifier = Modifier.weight(1f))

            // Enable notifications button
            val enableButtonInteractionSource = remember { MutableInteractionSource() }
            val isEnablePressed by enableButtonInteractionSource.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isEnablePressed) Color(0xFF2DC77A)
                        else SuccessGreen
                    )
                    .clickable(
                        interactionSource = enableButtonInteractionSource,
                        indication = null
                    ) { onEnableNotifications() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Enable Notifications",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Skip button
            val skipButtonInteractionSource = remember { MutableInteractionSource() }
            val isSkipPressed by skipButtonInteractionSource.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(
                        interactionSource = skipButtonInteractionSource,
                        indication = null
                    ) { onSkip() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Maybe Later",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSkipPressed) Color.White.copy(alpha = 0.5f)
                            else Color.White.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun NotificationBenefit(emoji: String, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

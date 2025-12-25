package com.example.socialapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.*
import kotlin.math.roundToInt

/**
 * Settings modal with slider for daily interaction quota
 * Styled similar to ActivityHistoryExpandedModal
 */
@Composable
fun SettingsModal(
    isVisible: Boolean,
    currentQuota: Int,
    onQuotaChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onViewPlanClick: () -> Unit,
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

            // Modal Content
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
                SettingsModalContent(
                    currentQuota = currentQuota,
                    onQuotaChange = onQuotaChange,
                    onDismiss = onDismiss,
                    onViewPlanClick = onViewPlanClick
                )
            }
        }
    }
}

@Composable
private fun SettingsModalContent(
    currentQuota: Int,
    onQuotaChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onViewPlanClick: () -> Unit
) {
    var sliderPosition by remember(currentQuota) { mutableFloatStateOf(currentQuota.toFloat()) }

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
            SettingsHeader(onDismiss = onDismiss)

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // Daily Quota Section
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                // Section title and current value
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily Interaction Quota",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = sliderPosition.roundToInt().toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = ActivityHigh,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Description
                Text(
                    text = "Set your daily conversation goal. The circle will fill as you progress.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Slider
                Slider(
                    value = sliderPosition,
                    onValueChange = { newValue ->
                        sliderPosition = newValue
                    },
                    onValueChangeFinished = {
                        onQuotaChange(sliderPosition.roundToInt())
                    },
                    valueRange = 1f..30f,
                    steps = 28, // 30 - 1 - 1 = 28 steps between 1 and 30
                    colors = SliderDefaults.colors(
                        thumbColor = ActivityHigh,
                        activeTrackColor = ActivityHigh,
                        inactiveTrackColor = BackgroundTertiary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Min and Max labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "1",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = "30",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // View Plan Row - Clean minimal design
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onViewPlanClick() }
                    .padding(vertical = Spacing.sm),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "👑",
                        fontSize = 24.sp
                    )
                    Text(
                        text = "View Plan",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = "›",
                    fontSize = 22.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
private fun SettingsHeader(onDismiss: () -> Unit) {
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
                text = "Settings",
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

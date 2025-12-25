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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.*

data class CancellationReason(
    val id: String,
    val emoji: String,
    val text: String
)

private val cancellationReasons = listOf(
    CancellationReason("price", "💰", "Too expensive"),
    CancellationReason("ui_ux", "🎨", "UI/UX not for me"),
    CancellationReason("experience", "😕", "Bad experience"),
    CancellationReason("not_needed", "🤷", "Don't need it anymore"),
    CancellationReason("found_match", "❤️", "Found a match!"),
    CancellationReason("other", "📝", "Other reason")
)

@Composable
fun SubscriptionModal(
    isVisible: Boolean,
    currentPlan: String = "Weekly",
    planPrice: String = "9.99 USD/week",
    onDismiss: () -> Unit,
    onCancelSubscription: (reason: String, comment: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCancelConfirmation by remember { mutableStateOf(false) }
    var showCancellationReasons by remember { mutableStateOf(false) }
    var isSubscriptionActive by remember { mutableStateOf(true) }
    var selectedReason by remember { mutableStateOf<String?>(null) }
    var comment by remember { mutableStateOf("") }

    // Reset state when modal is closed
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            showCancelConfirmation = false
            showCancellationReasons = false
            selectedReason = null
            comment = ""
        }
    }

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
                when {
                    showCancellationReasons -> {
                        CancellationReasonsContent(
                            selectedReason = selectedReason,
                            comment = comment,
                            onReasonSelected = { selectedReason = it },
                            onCommentChanged = { comment = it },
                            onBack = { showCancellationReasons = false },
                            onConfirmCancel = {
                                if (selectedReason != null) {
                                    isSubscriptionActive = false
                                    onCancelSubscription(selectedReason!!, comment)
                                    showCancellationReasons = false
                                    onDismiss()
                                }
                            }
                        )
                    }
                    showCancelConfirmation -> {
                        CancelConfirmationContent(
                            onKeepSubscription = { showCancelConfirmation = false },
                            onProceedCancel = {
                                showCancelConfirmation = false
                                showCancellationReasons = true
                            }
                        )
                    }
                    else -> {
                        SubscriptionPlanContent(
                            currentPlan = currentPlan,
                            planPrice = planPrice,
                            isSubscriptionActive = isSubscriptionActive,
                            onToggleSubscription = { newValue ->
                                if (!newValue && isSubscriptionActive) {
                                    showCancelConfirmation = true
                                } else {
                                    isSubscriptionActive = newValue
                                }
                            },
                            onDismiss = onDismiss
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubscriptionPlanContent(
    currentPlan: String,
    planPrice: String,
    isSubscriptionActive: Boolean,
    onToggleSubscription: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Plan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
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

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // Plan Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(CornerRadius.lg))
                    .background(SurfaceElevated)
                    .border(
                        width = 2.dp,
                        color = ActivityHigh,
                        shape = RoundedCornerShape(CornerRadius.lg)
                    )
                    .padding(Spacing.md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$currentPlan Access",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ActivityHigh.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isSubscriptionActive) "ACTIVE" else "CANCELLED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSubscriptionActive) ActivityHigh else Error
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = planPrice,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    Text(
                        text = "👑",
                        fontSize = 32.sp
                    )
                }
            }

            // Features List
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Text(
                    text = "Your Benefits",
                    style = MaterialTheme.typography.titleSmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                FeatureItem("Unlimited conversation tracking")
                FeatureItem("Daily motivation & reminders")
                FeatureItem("Progress insights & analytics")
                FeatureItem("Conversation tips & openers")
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // Subscription Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Auto-renewal",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isSubscriptionActive) "Your plan renews automatically"
                               else "Subscription cancelled",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                Switch(
                    checked = isSubscriptionActive,
                    onCheckedChange = onToggleSubscription,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = ActivityHigh,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SurfaceElevated,
                        uncheckedBorderColor = TextMuted
                    )
                )
            }
        }
    }
}

@Composable
private fun FeatureItem(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = ActivityHigh,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )
    }
}

@Composable
private fun CancelConfirmationContent(
    onKeepSubscription: () -> Unit,
    onProceedCancel: () -> Unit
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Warning Icon
            Text(
                text = "😢",
                fontSize = 48.sp
            )

            // Title
            Text(
                text = "Are you sure?",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Description
            Text(
                text = "You'll lose access to all premium features at the end of your billing period.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                // Keep Subscription Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(CornerRadius.md))
                        .background(ActivityHigh)
                        .clickable { onKeepSubscription() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Keep Subscription",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Cancel Anyway Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(CornerRadius.md))
                        .background(SurfaceElevated)
                        .clickable { onProceedCancel() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel Anyway",
                        style = MaterialTheme.typography.titleMedium,
                        color = Error,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun CancellationReasonsContent(
    selectedReason: String?,
    comment: String,
    onReasonSelected: (String) -> Unit,
    onCommentChanged: (String) -> Unit,
    onBack: () -> Unit,
    onConfirmCancel: () -> Unit
) {
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
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SurfaceElevated)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Help us improve",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(36.dp))
            }

            // Question
            Text(
                text = "What didn't work for you?",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Reason Options
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                cancellationReasons.forEach { reason ->
                    val isSelected = selectedReason == reason.id
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(CornerRadius.md))
                            .then(
                                if (isSelected) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = ActivityHigh,
                                        shape = RoundedCornerShape(CornerRadius.md)
                                    )
                                } else Modifier
                            )
                            .background(
                                if (isSelected) ActivityHigh.copy(alpha = 0.1f)
                                else SurfaceElevated
                            )
                            .clickable { onReasonSelected(reason.id) }
                            .padding(horizontal = Spacing.md, vertical = Spacing.sm + 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        Text(
                            text = reason.emoji,
                            fontSize = 20.sp
                        )
                        Text(
                            text = reason.text,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // Comment Box
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Text(
                    text = "Any additional feedback? (optional)",
                    style = MaterialTheme.typography.titleSmall,
                    color = TextSecondary
                )
                OutlinedTextField(
                    value = comment,
                    onValueChange = onCommentChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = {
                        Text(
                            text = "Tell us more about your experience...",
                            color = TextMuted
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = SurfaceElevated,
                        unfocusedContainerColor = SurfaceElevated,
                        focusedBorderColor = ActivityHigh,
                        unfocusedBorderColor = TextMuted.copy(alpha = 0.3f),
                        cursorColor = ActivityHigh
                    ),
                    shape = RoundedCornerShape(CornerRadius.md)
                )
            }

            // Submit Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(CornerRadius.md))
                    .background(
                        if (selectedReason != null) Error else SurfaceElevated
                    )
                    .then(
                        if (selectedReason != null) {
                            Modifier.clickable { onConfirmCancel() }
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Confirm Cancellation",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (selectedReason != null) Color.White else TextMuted,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

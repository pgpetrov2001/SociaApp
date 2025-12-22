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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.*
import kotlin.math.roundToInt

/**
 * Modal for adding a new interaction with quality rating and note
 */
@Composable
fun AddInteractionModal(
    isVisible: Boolean,
    onSave: (qualityRating: Int, noteText: String) -> Unit,
    onDismiss: () -> Unit,
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
                AddInteractionModalContent(
                    onSave = onSave,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
private fun AddInteractionModalContent(
    onSave: (qualityRating: Int, noteText: String) -> Unit,
    onDismiss: () -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(2f) } // Default to "Good"
    var noteText by remember { mutableStateOf("") }
    val maxChars = 200

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
            AddInteractionHeader(onDismiss = onDismiss)

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // Quality Rating Section
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Text(
                    text = "How did it go?",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                // Emoji and quality label
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    // Large emoji
                    Text(
                        text = getQualityEmoji(sliderPosition.roundToInt()),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 80.sp
                        )
                    )

                    // Quality label
                    Text(
                        text = getQualityLabel(sliderPosition.roundToInt()),
                        style = MaterialTheme.typography.headlineSmall,
                        color = ActivityHigh,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Slider
                Slider(
                    value = sliderPosition,
                    onValueChange = { newValue ->
                        sliderPosition = newValue
                    },
                    valueRange = 1f..4f,
                    steps = 2, // Creates 4 distinct positions
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
                        text = "Poor",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = "Amazing",
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

            // Note TextField Section
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Text(
                    text = "Add a note (optional)",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = noteText,
                    onValueChange = { newText ->
                        if (newText.length <= maxChars) {
                            noteText = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "What happened?",
                            color = TextMuted
                        )
                    },
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ActivityHigh,
                        unfocusedBorderColor = TextMuted.copy(alpha = 0.3f),
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = ActivityHigh
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(CornerRadius.md)
                )

                // Character counter
                Text(
                    text = "${noteText.length} / $maxChars",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            // Save Button
            Button(
                onClick = {
                    onSave(sliderPosition.roundToInt(), noteText)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActivityHigh,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(CornerRadius.md)
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Spacing.xs)
                )
            }
        }
    }
}

@Composable
private fun AddInteractionHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Add Interaction",
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
}

private fun getQualityEmoji(rating: Int): String = when(rating) {
    1 -> "😞"
    2 -> "🙂"
    3 -> "😊"
    4 -> "😍"
    else -> "🙂"
}

private fun getQualityLabel(rating: Int): String = when(rating) {
    1 -> "Poor"
    2 -> "Good"
    3 -> "Great"
    4 -> "Amazing"
    else -> "Good"
}

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.data.InteractionNote
import com.example.socialapp.ui.theme.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Modal displaying all saved interaction notes
 */
@Composable
fun NotesHistoryModal(
    isVisible: Boolean,
    interactions: List<InteractionNote>,
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
                NotesHistoryModalContent(
                    interactions = interactions,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
private fun NotesHistoryModalContent(
    interactions: List<InteractionNote>,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .fillMaxHeight(0.85f)
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Header with close button
            NotesHistoryHeader(onDismiss = onDismiss)

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )

            // List of notes or empty state
            if (interactions.isEmpty()) {
                EmptyNotesState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    items(interactions.sortedByDescending { it.timestamp }) { interaction ->
                        NoteListItem(interaction = interaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotesHistoryHeader(onDismiss: () -> Unit) {
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
                text = "💬",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Notes History",
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

@Composable
private fun NoteListItem(interaction: InteractionNote) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadius.md))
            .background(SurfaceElevated)
            .padding(Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Left: Emoji
        Text(
            text = getQualityEmoji(interaction.qualityRating),
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 48.sp
            )
        )

        // Right: Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            // Rating label + timestamp row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getQualityLabel(interaction.qualityRating),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatTimestamp(interaction.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }

            // Note text (if not empty)
            if (interaction.noteText.isNotEmpty()) {
                Text(
                    text = interaction.noteText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun EmptyNotesState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📝",
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 72.sp
            )
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = "No notes yet",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(Spacing.xs))
        Text(
            text = "Add your first interaction to get started",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted
        )
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

private fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val dateTime = instant.atZone(ZoneId.systemDefault())
    val date = dateTime.toLocalDate()
    val time = dateTime.toLocalTime()

    val today = LocalDate.now()
    val dateStr = when {
        date == today -> "Today"
        date == today.minusDays(1) -> "Yesterday"
        else -> date.format(DateTimeFormatter.ofPattern("MMM d"))
    }

    val timeStr = time.format(DateTimeFormatter.ofPattern("h:mm a"))
    return "$dateStr, $timeStr"
}

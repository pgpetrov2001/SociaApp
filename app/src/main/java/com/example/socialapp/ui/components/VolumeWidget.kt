package com.example.socialapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.socialapp.ui.theme.Spacing
import com.example.socialapp.ui.theme.TextMuted
import com.example.socialapp.ui.theme.TextPrimary
import com.example.socialapp.ui.theme.TextSecondary

/**
 * Widget 4: Notes History widget (full width, half height)
 */
@Composable
fun NotesHistoryWidget(
    notesCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    WidgetCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Notes History",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = "$notesCount ${if (notesCount == 1) "note" else "notes"}",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )
        }
    }
}

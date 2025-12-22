package com.example.socialapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialapp.ui.theme.Spacing
import com.example.socialapp.ui.theme.TextMuted
import com.example.socialapp.ui.theme.TextPrimary
import com.example.socialapp.ui.theme.TextSecondary

/**
 * Widget 2: Lifetime total conversations widget
 */
@Composable
fun LifetimeWidget(
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    WidgetCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = Spacing.xs),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Large number - iOS style like workout app
            Text(
                text = totalCount.toString(),
                fontSize = 64.sp,
                fontWeight = FontWeight.W600,
                letterSpacing = (-1.5).sp,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            // "Total conversations" label - medium weight like workout app
            Text(
                text = "Total conversations",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                ),
                color = TextSecondary,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}

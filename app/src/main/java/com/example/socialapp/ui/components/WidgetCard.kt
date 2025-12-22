package com.example.socialapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.example.socialapp.ui.theme.CornerRadius
import com.example.socialapp.ui.theme.Elevation
import com.example.socialapp.ui.theme.Spacing
import com.example.socialapp.ui.theme.SurfaceElevated

/**
 * Reusable neumorphic-style widget card component
 * Follows iOS design system with subtle shadows and rounded corners
 */
@Composable
fun WidgetCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = SurfaceElevated,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(CornerRadius.lg)

    Box(
        modifier = modifier
            .shadow(
                elevation = Elevation.card,
                shape = shape,
                spotColor = Color.Black.copy(alpha = 0.4f),
                ambientColor = Color.White.copy(alpha = 0.05f)
            )
            .clip(shape)
            .background(backgroundColor)
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(Spacing.md)
    ) {
        content()
    }
}

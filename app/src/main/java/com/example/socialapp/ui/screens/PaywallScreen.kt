package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colors
private val LightBackground = Color(0xFFF0F0FF)
private val PrimaryBlue = Color(0xFF4A4AE8)
private val AccentBlue = Color(0xFF6366F1)
private val DarkText = Color(0xFF1F2937)
private val GrayText = Color(0xFF6B7280)
private val FeatureIconBg = Color(0xFFE8E8FF)
private val CardBackground = Color.White
private val SelectedCardBorder = Color(0xFF6366F1)
private val StarColor = Color(0xFFFFD700)

@Composable
fun PaywallScreen(
    onNavigateNext: () -> Unit,
    onClose: () -> Unit
) {
    var selectedPlan by remember { mutableStateOf(0) } // 0 = weekly, 1 = yearly

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = buildAnnotatedString {
                    append("Hey ")
                    withStyle(style = SpanStyle(color = PrimaryBlue)) {
                        append("Friend")
                    }
                    append(", unlock ")
                    withStyle(style = SpanStyle(color = PrimaryBlue)) {
                        append("Social")
                    }
                    withStyle(style = SpanStyle(color = AccentBlue)) {
                        append("App")
                    }
                    append(" to reach your goals faster.")
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Features
            FeatureRow(emoji = "📊", text = "Track Your Progress & See Insights")
            Spacer(modifier = Modifier.height(12.dp))
            FeatureRow(emoji = "🎯", text = "Set Personal Goals & Achieve Them")
            Spacer(modifier = Modifier.height(12.dp))
            FeatureRow(emoji = "🌸", text = "Access Your Complete Wellness Journey")
            Spacer(modifier = Modifier.height(12.dp))
            FeatureRow(
                emoji = "✅",
                text = buildAnnotatedString {
                    append("Save ")
                    withStyle(style = SpanStyle(color = PrimaryBlue, fontWeight = FontWeight.Bold)) {
                        append("\$120")
                    }
                    append(" Yearly With Premium!")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Pricing Cards
            // Weekly Plan
            PricingCard(
                title = "Weekly access",
                subtitle = "3 days free trial",
                badgeText = "BEST OFFER",
                showBadge = true,
                tagText = "FREE",
                tagColor = PrimaryBlue,
                originalPrice = "9.99 USD",
                priceLabel = "per week",
                isSelected = selectedPlan == 0,
                onClick = { selectedPlan = 0 }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Yearly Plan
            PricingCard(
                title = "Yearly access",
                subtitle = "3 days free trial",
                showBadge = false,
                tagText = "Save 90%",
                tagColor = Color(0xFF10B981),
                originalPrice = null,
                price = "1.99 USD",
                priceLabel = "per week",
                isSelected = selectedPlan == 1,
                onClick = { selectedPlan = 1 }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Star Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) {
                    Text(
                        text = "⭐",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // No Payment Due text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = GrayText,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "No Payment Due Now. Cancel Anytime",
                    fontSize = 14.sp,
                    color = GrayText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Start Free Trial Button
            val buttonInteraction = remember { MutableInteractionSource() }
            val isButtonPressed by buttonInteraction.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isButtonPressed) PrimaryBlue.copy(alpha = 0.8f)
                        else PrimaryBlue
                    )
                    .clickable(
                        interactionSource = buttonInteraction,
                        indication = null
                    ) { onNavigateNext() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start Free Trial",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Trial info text
            Text(
                text = "3 days free trial then ${if (selectedPlan == 0) "9.99 USD/week" else "99.99 USD/year"}",
                fontSize = 13.sp,
                color = GrayText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Terms links
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Terms of use",
                    fontSize = 12.sp,
                    color = GrayText,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
                Text(
                    text = "Restore purchase",
                    fontSize = 12.sp,
                    color = GrayText,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
                Text(
                    text = "Privacy Policy",
                    fontSize = 12.sp,
                    color = GrayText,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun FeatureRow(
    emoji: String,
    text: String
) {
    FeatureRow(
        emoji = emoji,
        text = buildAnnotatedString { append(text) }
    )
}

@Composable
private fun FeatureRow(
    emoji: String,
    text: androidx.compose.ui.text.AnnotatedString
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FeatureIconBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = DarkText
        )
    }
}

@Composable
private fun PricingCard(
    title: String,
    subtitle: String,
    showBadge: Boolean = false,
    badgeText: String = "",
    tagText: String,
    tagColor: Color,
    originalPrice: String? = null,
    price: String? = null,
    priceLabel: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (showBadge) 12.dp else 0.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardBackground)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) SelectedCardBorder else Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = GrayText
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Tag (FREE or Save 90%)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(tagColor.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tagText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = tagColor
                        )
                    }

                    // Price
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        if (originalPrice != null) {
                            Text(
                                text = originalPrice,
                                fontSize = 14.sp,
                                color = GrayText,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        if (price != null) {
                            Text(
                                text = price,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        }
                        Text(
                            text = priceLabel,
                            fontSize = 12.sp,
                            color = GrayText
                        )
                    }
                }
            }
        }

        // Badge (BEST OFFER)
        if (showBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(PrimaryBlue)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

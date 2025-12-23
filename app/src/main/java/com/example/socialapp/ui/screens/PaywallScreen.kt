package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PricingTier(
    val name: String,
    val price: String,
    val originalPrice: String? = null,
    val hasBadge: Boolean = false,
    val badgeText: String = "",
    val hasFreeTrial: Boolean = true
)

@Composable
fun PaywallScreen(
    onNavigateNext: () -> Unit,
    onClose: () -> Unit
) {
    var selectedTier by remember { mutableStateOf(1) } // 0, 1, or 2
    var isMonthly by remember { mutableStateOf(true) }

    val pricingTiers = listOf(
        PricingTier(
            name = "Premium Monthly",
            price = "$19.99",
            hasFreeTrial = true
        ),
        PricingTier(
            name = "Premium+ Monthly",
            price = "$29.99",
            originalPrice = "$59.99",
            hasBadge = true,
            badgeText = "Early Bird Discount",
            hasFreeTrial = true
        ),
        PricingTier(
            name = "Pro Monthly",
            price = "$79.99",
            originalPrice = "$129.99",
            hasFreeTrial = true
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose your plan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClose() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Monthly/Yearly toggle
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1C1C1E))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Monthly
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isMonthly) Color.White
                            else Color.Transparent
                        )
                        .clickable { isMonthly = true }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Monthly",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isMonthly) Color.Black else Color(0xFF8E8E93)
                    )
                }

                // Yearly
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (!isMonthly) Color.White
                            else Color.Transparent
                        )
                        .clickable { isMonthly = false }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Yearly",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (!isMonthly) Color.Black else Color(0xFF8E8E93)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pricing tiers
            pricingTiers.forEachIndexed { index, tier ->
                PricingTierCard(
                    tier = tier,
                    isSelected = index == selectedTier,
                    onClick = {
                        selectedTier = index
                        onNavigateNext()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // What do I get section
            Text(
                text = "What do I get?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Features
            FeatureItem(
                icon = "📈",
                title = "Trending Insights",
                description = "Uncover data-backed betting opportunities in a curated feed."
            )

            Spacer(modifier = Modifier.height(16.dp))

            FeatureItem(
                icon = "💡",
                title = "Thousands of Props & Games",
                description = "All the props, teams and leagues refreshed each day."
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Continue button
            val continueButtonInteraction = remember { MutableInteractionSource() }
            val isContinuePressed by continueButtonInteraction.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isContinuePressed) Color(0xFFE5E5E7)
                        else Color.White
                    )
                    .clickable(
                        interactionSource = continueButtonInteraction,
                        indication = null
                    ) { onNavigateNext() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Promo code link
            Text(
                text = "I HAVE A PROMO CODE",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF8E8E93),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle promo code */ },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PricingTierCard(
    tier: PricingTier,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1C1C1E))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        // Checkmark for selected tier
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFF00D9CC), shape = CircleShape)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Free trial badge
            if (tier.hasFreeTrial) {
                Text(
                    text = "1-week trial",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF00D9CC),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = tier.name.substringBefore(" Monthly"),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Monthly",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Monthly billing",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF8E8E93),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = tier.price,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    if (tier.originalPrice != null) {
                        Text(
                            text = tier.originalPrice,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF8E8E93),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }

            // Early Bird Discount badge
            if (tier.hasBadge) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF00D9CC))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = tier.badgeText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(
    icon: String,
    title: String,
    description: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8E8E93),
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

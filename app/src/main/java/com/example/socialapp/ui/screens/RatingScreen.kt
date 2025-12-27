package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colors matching OnboardingScreen
private val DarkNavyBackground = Color(0xFF0D1B2A)
private val CardBackground = Color(0xFF1B2838)
private val AccentBlue = Color(0xFF4A90D9)
private val AccentGreen = Color(0xFF39D98A)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFF8E8E93)
private val QuoteColor = Color(0xFF4A90D9)

data class Testimonial(
    val emoji: String,
    val name: String,
    val age: Int,
    val rating: Int,
    val quote: String
)

private val testimonials = listOf(
    Testimonial(
        emoji = "👨",
        name = "Mike",
        age = 26,
        rating = 5,
        quote = "After months of struggling to talk to women, this app changed everything. The daily challenges pushed me out of my comfort zone. Now I'm dating someone amazing!"
    ),
    Testimonial(
        emoji = "🧔",
        name = "James",
        age = 31,
        rating = 5,
        quote = "I used to freeze up every time I wanted to approach someone. The confidence tips and tracking features helped me see real progress. Highly recommend!"
    ),
    Testimonial(
        emoji = "👱",
        name = "Alex",
        age = 24,
        rating = 5,
        quote = "The daily reminders kept me accountable. Went from zero approaches to getting numbers regularly. This app actually works when you put in the effort!"
    )
)

@Composable
fun RatingScreen(
    onNavigateNext: () -> Unit
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Give us a rating",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Heart emoji
                Text(
                    text = "❤️",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 5 Stars
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) {
                        Text(
                            text = "⭐",
                            fontSize = 32.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Encouraging text
                Text(
                    text = "Users who left reviews were 27% more likely to successfully reach their dating goals.",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Title with app name highlighted
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = AccentBlue)) {
                            append("Social")
                        }
                        withStyle(style = SpanStyle(color = AccentGreen)) {
                            append("App")
                        }
                        append(" was made for people like you")
                    },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Testimonial cards - show only first one prominently like screenshot
                TestimonialCard(testimonial = testimonials[0])

                Spacer(modifier = Modifier.height(12.dp))

                // Additional testimonials
                testimonials.drop(1).forEach { testimonial ->
                    TestimonialCard(testimonial = testimonial)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Continue button
            val buttonInteraction = remember { MutableInteractionSource() }
            val isPressed by buttonInteraction.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isPressed) Color(0xFFD0D0D0)
                        else Color(0xFFAEAEB2)
                    )
                    .clickable(
                        interactionSource = buttonInteraction,
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

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun TestimonialCard(testimonial: Testimonial) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column {
            // Header: Avatar, Name, Age, Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar circle with emoji - gold/yellow background
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD4A256)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = testimonial.emoji,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    // Name and Age
                    Text(
                        text = "${testimonial.name}, ${testimonial.age}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )

                    // Stars
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        repeat(testimonial.rating) {
                            Text(
                                text = "⭐",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quote marks - teal/blue color
            Text(
                text = "❝",
                fontSize = 24.sp,
                color = Color(0xFF5BA8A0),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Quote text
            Text(
                text = testimonial.quote,
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

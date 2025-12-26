package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

// Data class for interstitial messages
data class InterstitialMessage(
    val normalText: String,
    val boldText: String
)

// All possible interstitial messages organized by category
object InterstitialMessages {

    // Messages for after Q3 (based on challenge, age, approach frequency)
    val fearBasedMessages = listOf(
        InterstitialMessage(
            "Thousands of people who felt the same",
            "fear of rejection now approach with confidence."
        ),
        InterstitialMessage(
            "You're not alone. Most users started with",
            "the same fears you have right now."
        ),
        InterstitialMessage(
            "Fear of rejection is more common than you think.",
            "We've helped thousands overcome it."
        ),
        InterstitialMessage(
            "The anxiety you feel is normal.",
            "Every confident person started exactly where you are."
        )
    )

    val shyBasedMessages = listOf(
        InterstitialMessage(
            "Being shy doesn't mean you can't succeed.",
            "It means you care - and that's a strength."
        ),
        InterstitialMessage(
            "Most confident people weren't born that way.",
            "They practiced, just like you're about to."
        ),
        InterstitialMessage(
            "Thousands of shy users have transformed",
            "into confident communicators with daily practice."
        ),
        InterstitialMessage(
            "Shyness isn't a weakness.",
            "With the right tools, it becomes your superpower."
        )
    )

    val socialStruggleMessages = listOf(
        InterstitialMessage(
            "Meeting new people is a skill, not luck.",
            "And skills can be trained."
        ),
        InterstitialMessage(
            "Getting ghosted happens to everyone.",
            "The winners are those who keep showing up."
        ),
        InterstitialMessage(
            "You're not bad at socializing.",
            "You just haven't had the right system until now."
        ),
        InterstitialMessage(
            "Thousands struggled to meet people too.",
            "Now they're building real connections daily."
        )
    )

    val nervousBasedMessages = listOf(
        InterstitialMessage(
            "That nervous feeling? It's excitement in disguise.",
            "We'll help you channel it into confidence."
        ),
        InterstitialMessage(
            "Users who felt too nervous to approach",
            "now start conversations effortlessly."
        ),
        InterstitialMessage(
            "Nervousness fades with practice.",
            "We'll guide you one small step at a time."
        ),
        InterstitialMessage(
            "You're not the only one who freezes up.",
            "Confidence isn't talent - it's trained."
        )
    )

    // Messages for after Q6 (based on blockers, goals, help preference)
    val confidenceGoalMessages = listOf(
        InterstitialMessage(
            "Users who wanted to build confidence report",
            "feeling unstoppable after just 2 weeks."
        ),
        InterstitialMessage(
            "Confidence grows through action, not motivation.",
            "That's why we guide you daily."
        ),
        InterstitialMessage(
            "You're closer to confidence than you think.",
            "Small wins compound fast when tracked."
        ),
        InterstitialMessage(
            "Thousands have built unshakeable confidence.",
            "Your transformation starts today."
        )
    )

    val datingGoalMessages = listOf(
        InterstitialMessage(
            "More dates come from more conversations.",
            "We'll help you start them naturally."
        ),
        InterstitialMessage(
            "Users focused on dating see results",
            "within their first week of practice."
        ),
        InterstitialMessage(
            "You're not bad at dating.",
            "You just haven't practiced enough - yet."
        ),
        InterstitialMessage(
            "Finding a girlfriend starts with showing up.",
            "We help you show up daily."
        )
    )

    val socialGoalMessages = listOf(
        InterstitialMessage(
            "Becoming more social is easier than you think.",
            "Daily micro-actions create massive change."
        ),
        InterstitialMessage(
            "Users who wanted a better social life report",
            "more meaningful connections than ever."
        ),
        InterstitialMessage(
            "Your social skills are about to level up.",
            "One conversation at a time."
        ),
        InterstitialMessage(
            "A vibrant social life is built daily.",
            "We'll make sure you never skip a day."
        )
    )

    val disciplineMessages = listOf(
        InterstitialMessage(
            "Motivation fades. Discipline stays.",
            "That's why we remind you when it matters."
        ),
        InterstitialMessage(
            "Most people fail because they're inconsistent.",
            "You won't be - we'll keep you accountable."
        ),
        InterstitialMessage(
            "Small daily actions beat big plans.",
            "We turn intentions into habits."
        ),
        InterstitialMessage(
            "Progress feels invisible until it's tracked.",
            "That's where clarity comes from."
        )
    )

    val mindsetMessages = listOf(
        InterstitialMessage(
            "Your mindset determines your success.",
            "We'll help you think like a confident person."
        ),
        InterstitialMessage(
            "Overthinking kills more opportunities than rejection.",
            "We'll train you to act first, think less."
        ),
        InterstitialMessage(
            "Past rejections don't define you.",
            "They're just reps that build resilience."
        ),
        InterstitialMessage(
            "Confidence isn't a personality trait.",
            "It's a habit - and habits can be built."
        )
    )

    val belongingMessages = listOf(
        InterstitialMessage(
            "You're joining thousands of people just like you.",
            "Same fears. Same goals. Same starting point."
        ),
        InterstitialMessage(
            "Thousands started nervous and unsure.",
            "They didn't wait to feel ready - they trained readiness."
        ),
        InterstitialMessage(
            "You're not late. You're right on time.",
            "Most people never take this step."
        ),
        InterstitialMessage(
            "This is where avoidance turns into action.",
            "And action turns into confidence."
        )
    )
}

// Function to generate message based on user answers
fun generateInterstitialMessage(
    interstitialNumber: Int, // 1 or 2
    answers: Map<Int, String> // step -> selected answer text
): InterstitialMessage {

    if (interstitialNumber == 1) {
        // After Q3: analyze challenge (Q1), age (Q2), approach frequency (Q3)
        val challenge = answers[1] ?: ""
        val approachFrequency = answers[3] ?: ""

        return when {
            // Fear-based challenges
            challenge.contains("Fear of rejection", ignoreCase = true) ||
            challenge.contains("Past rejections", ignoreCase = true) ->
                InterstitialMessages.fearBasedMessages.random()

            // Shyness and nervousness
            challenge.contains("shy", ignoreCase = true) ||
            approachFrequency.contains("nervous", ignoreCase = true) ||
            approachFrequency.contains("Never", ignoreCase = true) ->
                InterstitialMessages.shyBasedMessages.random()

            // Social struggles
            challenge.contains("ghosted", ignoreCase = true) ||
            challenge.contains("can't meet", ignoreCase = true) ->
                InterstitialMessages.socialStruggleMessages.random()

            // Not knowing what to say / freezing
            challenge.contains("Not knowing what to say", ignoreCase = true) ||
            approachFrequency.contains("Rarely", ignoreCase = true) ->
                InterstitialMessages.nervousBasedMessages.random()

            // Default fallback
            else -> InterstitialMessages.belongingMessages.random()
        }
    } else {
        // After Q6: analyze blockers (Q4), goals (Q5), help preference (Q6)
        val blocker = answers[4] ?: ""
        val goal = answers[5] ?: ""
        val helpPref = answers[6] ?: ""

        return when {
            // Confidence focused goals
            goal.contains("confidence", ignoreCase = true) ->
                InterstitialMessages.confidenceGoalMessages.random()

            // Dating focused goals
            goal.contains("dates", ignoreCase = true) ||
            goal.contains("girlfriend", ignoreCase = true) ->
                InterstitialMessages.datingGoalMessages.random()

            // Social life goals
            goal.contains("conversations", ignoreCase = true) ||
            goal.contains("social overall", ignoreCase = true) ->
                InterstitialMessages.socialGoalMessages.random()

            // Help preferences - discipline/tracking
            helpPref.contains("reminders", ignoreCase = true) ||
            helpPref.contains("Track", ignoreCase = true) ||
            helpPref.contains("challenges", ignoreCase = true) ->
                InterstitialMessages.disciplineMessages.random()

            // Mindset blockers
            blocker.contains("Overthinking", ignoreCase = true) ||
            blocker.contains("confidence", ignoreCase = true) ||
            blocker.contains("rejections hurt", ignoreCase = true) ||
            helpPref.contains("Mindset", ignoreCase = true) ->
                InterstitialMessages.mindsetMessages.random()

            // Default - belonging
            else -> InterstitialMessages.belongingMessages.random()
        }
    }
}

// Colors matching OnboardingScreen
private val DarkNavyBackground = Color(0xFF0D1B2A)
private val ProgressActiveColor = Color(0xFF4A90D9)

@Composable
fun InterstitialScreen(
    interstitialNumber: Int,
    answers: Map<Int, String>,
    onNavigateNext: () -> Unit
) {
    val message = remember(interstitialNumber, answers) {
        generateInterstitialMessage(interstitialNumber, answers)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Progress indicator (visual only - shows we're between questions)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xFF4A5568))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(if (interstitialNumber == 1) 0.5f else 1f)
                        .clip(RoundedCornerShape(3.dp))
                        .background(ProgressActiveColor)
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Message text - matching the question title style
            Text(
                text = buildAnnotatedString {
                    append(message.normalText + " ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(message.boldText)
                    }
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            // Continue button - matching OnboardingScreen style
            val buttonInteractionSource = remember { MutableInteractionSource() }
            val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (isButtonPressed) Color(0xFFD0D0D0)
                        else Color(0xFFAEAEB2)
                    )
                    .clickable(
                        interactionSource = buttonInteractionSource,
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

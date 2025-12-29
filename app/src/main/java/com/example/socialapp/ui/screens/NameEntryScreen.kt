package com.example.socialapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkNavyBackground = Color(0xFF0D1B2A)
private val CardBackground = Color(0xFF1B2838)
private val ContinueButtonDisabled = Color(0xFF3A3A3C)
private val ContinueTextDisabled = Color(0xFF8E8E93)
private val AccentBlue = Color(0xFF4A90D9)

@Composable
fun NameEntryScreen(
    onNavigateNext: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("Georgi") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
            Spacer(modifier = Modifier.height(80.dp))

            // Welcome emoji
            Text(
                text = "👋",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "What's your name?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = "Let's personalize your experience",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Name input field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardBackground)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(AccentBlue),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (name.isNotBlank()) {
                                keyboardController?.hide()
                                onNavigateNext(name.trim())
                            }
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            if (name.isEmpty()) {
                                Text(
                                    text = "Enter your name",
                                    fontSize = 18.sp,
                                    color = Color.White.copy(alpha = 0.4f),
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Continue button
            val buttonInteractionSource = remember { MutableInteractionSource() }
            val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()
            val isEnabled = name.isNotBlank()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        when {
                            !isEnabled -> ContinueButtonDisabled
                            isButtonPressed -> Color(0xFFD0D0D0)
                            else -> Color(0xFFAEAEB2)
                        }
                    )
                    .then(
                        if (isEnabled) {
                            Modifier.clickable(
                                interactionSource = buttonInteractionSource,
                                indication = null
                            ) {
                                keyboardController?.hide()
                                onNavigateNext(name.trim())
                            }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isEnabled) Color.Black else ContinueTextDisabled
                )
            }

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

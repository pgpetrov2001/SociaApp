package com.example.socialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.socialapp.navigation.Screen
import com.example.socialapp.ui.screens.*
import com.example.socialapp.ui.theme.SocialAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialAppTheme(darkTheme = true, dynamicColor = false) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
    var previousScreen by remember { mutableStateOf<Screen?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B2A))
    ) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                val isForward = isForwardNavigation(previousScreen, targetState)

                if (isForward) {
                    // Forward navigation: slide in from right with crossfade
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    ).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth / 4 },
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        )
                    ).using(
                        SizeTransform(clip = false)
                    )
                } else {
                    // Backward navigation: slide in from left
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth / 4 },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    ).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        )
                    ).using(
                        SizeTransform(clip = false)
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            label = "screen_transition"
        ) { screen ->
        when (screen) {
            is Screen.Splash -> {
                SplashScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Onboarding(1)
                    }
                )
            }

            is Screen.Onboarding -> {
                val step = screen.step
                OnboardingScreen(
                    step = step,
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = when (step) {
                            in 1..5 -> Screen.Onboarding(step + 1)
                            else -> Screen.Loading
                        }
                    },
                    onNavigateBack = {
                        previousScreen = currentScreen
                        currentScreen = when (step) {
                            1 -> Screen.Splash
                            else -> Screen.Onboarding(step - 1)
                        }
                    },
                    onSkip = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Loading
                    }
                )
            }

            is Screen.Loading -> {
                LoadingScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Results
                    }
                )
            }

            is Screen.Results -> {
                ResultsScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Login
                    }
                )
            }

            is Screen.Login -> {
                LoginScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Paywall
                    }
                )
            }

            is Screen.Paywall -> {
                PaywallScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Rating
                    },
                    onClose = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Rating
                    }
                )
            }

            is Screen.Rating -> {
                RatingScreen(
                    onNavigateNext = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Main
                    }
                )
            }

            is Screen.Main -> {
                SocialTrackerScreen(modifier = Modifier.fillMaxSize())
            }
        }
        }
    }
}

private fun isForwardNavigation(previous: Screen?, current: Screen): Boolean {
    val screenOrder = listOf(
        Screen.Splash::class,
        Screen.Onboarding::class,
        Screen.Loading::class,
        Screen.Results::class,
        Screen.Login::class,
        Screen.Paywall::class,
        Screen.Rating::class,
        Screen.Main::class
    )

    if (previous == null) return true

    // Special case for onboarding steps
    if (previous is Screen.Onboarding && current is Screen.Onboarding) {
        return current.step > previous.step
    }

    val previousIndex = screenOrder.indexOfFirst { it.isInstance(previous) }
    val currentIndex = screenOrder.indexOfFirst { it.isInstance(current) }

    return currentIndex > previousIndex
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun SocialTrackerScreenPreview() {
    SocialAppTheme(darkTheme = true, dynamicColor = false) {
        SocialTrackerScreen()
    }
}
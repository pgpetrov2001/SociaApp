package com.example.socialapp.navigation

sealed class Screen {
    object Splash : Screen()
    data class Onboarding(val step: Int) : Screen() // 1-6
    data class Interstitial(val number: Int) : Screen() // 1 (after Q3), 2 (after Q6)
    object Loading : Screen()
    object Results : Screen()
    object Login : Screen()
    object Paywall : Screen()
    object Rating : Screen()
    object Main : Screen()
}

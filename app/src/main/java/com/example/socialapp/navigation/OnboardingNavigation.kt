package com.example.socialapp.navigation

sealed class Screen {
    object Splash : Screen()
    data class Onboarding(val step: Int) : Screen() // 1, 2, or 3
    object Loading : Screen()
    object Results : Screen()
    object Login : Screen()
    object Paywall : Screen()
    object Main : Screen()
}

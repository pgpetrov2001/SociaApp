package com.example.socialapp.data

import java.time.LocalDate

data class DayActivity(
    val date: LocalDate,
    val isActive: Boolean
)

data class SocialStats(
    val todayCount: Int,
    val lifetimeTotal: Int,
    val totalConversations: Int,
    val activityHistory: List<DayActivity>,
    val streakCount: Int
) {
    val activeDays: Int
        get() = activityHistory.count { it.isActive }
}

// Sample data generator
fun generateSampleData(): SocialStats {
    val today = LocalDate.now()
    val activityHistory = mutableListOf<DayActivity>()

    // Generate 90 days of activity
    for (i in 89 downTo 0) {
        val date = today.minusDays(i.toLong())
        // Random activity pattern - about 75% active days
        val isActive = (Math.random() * 100).toInt() < 75
        activityHistory.add(DayActivity(date, isActive))
    }

    return SocialStats(
        todayCount = 7,
        lifetimeTotal = 0,
        totalConversations = 250,
        activityHistory = activityHistory,
        streakCount = 7
    )
}

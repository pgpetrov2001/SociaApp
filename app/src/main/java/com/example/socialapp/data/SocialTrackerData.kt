package com.example.socialapp.data

import java.time.LocalDate
import java.time.YearMonth

data class DayActivity(
    val date: LocalDate,
    val interactionCount: Int
) {
    val isActive: Boolean get() = interactionCount > 0
}

data class MonthActivity(
    val yearMonth: YearMonth,
    val days: List<DayActivity>,
    val totalInteractions: Int
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

    val longestStreak: Int
        get() {
            var maxStreak = 0
            var currentStreak = 0
            for (day in activityHistory.sortedBy { it.date }) {
                if (day.isActive) {
                    currentStreak++
                    maxStreak = maxOf(maxStreak, currentStreak)
                } else {
                    currentStreak = 0
                }
            }
            return maxStreak
        }

    val mostActiveDay: DayActivity?
        get() = activityHistory.maxByOrNull { it.interactionCount }

    fun getMonthlyActivity(months: Int = 6): List<MonthActivity> {
        val today = LocalDate.now()
        val result = mutableListOf<MonthActivity>()

        for (i in 0 until months) {
            val targetMonth = YearMonth.now().minusMonths(i.toLong())
            val daysInMonth = activityHistory.filter {
                YearMonth.from(it.date) == targetMonth
            }.sortedBy { it.date }

            val totalInteractions = daysInMonth.sumOf { it.interactionCount }
            result.add(MonthActivity(targetMonth, daysInMonth, totalInteractions))
        }

        return result
    }
}

// Sample data generator
fun generateSampleData(): SocialStats {
    val today = LocalDate.now()
    val activityHistory = mutableListOf<DayActivity>()

    // Generate 180 days of activity (6 months)
    for (i in 179 downTo 0) {
        val date = today.minusDays(i.toLong())
        // Generate realistic distribution of interactions
        val interactionCount = when ((0..100).random()) {
            in 0..20 -> 0           // 20% no activity
            in 21..45 -> (1..2).random()   // 25% low activity
            in 46..70 -> (3..4).random()   // 25% medium activity
            in 71..90 -> (5..7).random()   // 20% high activity
            else -> (8..12).random()        // 10% very high activity
        }
        activityHistory.add(DayActivity(date, interactionCount))
    }

    return SocialStats(
        todayCount = 7,
        lifetimeTotal = activityHistory.sumOf { it.interactionCount },
        totalConversations = activityHistory.sumOf { it.interactionCount },
        activityHistory = activityHistory,
        streakCount = 7
    )
}

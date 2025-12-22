package com.example.socialapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for Social Tracker screen following MVVM architecture
 *
 * Manages:
 * - Today's conversation count
 * - Total conversation count
 * - Daily interaction quota
 *
 * TODO: Add data persistence using Room database or SharedPreferences
 * for saving state across app restarts
 */
class SocialTrackerViewModel : ViewModel() {

    // Private mutable state
    private val _uiState = MutableStateFlow(SocialTrackerUiState())

    // Public immutable state
    val uiState: StateFlow<SocialTrackerUiState> = _uiState.asStateFlow()

    /**
     * Increment both today's count and total conversations
     * Called when the FAB (add button) is pressed
     */
    fun incrementConversations() {
        _uiState.update { currentState ->
            currentState.copy(
                todayCount = currentState.todayCount + 1,
                totalCount = currentState.totalCount + 1
            )
        }
    }

    /**
     * Update the daily quota setting
     * @param quota New daily quota value (1-30)
     */
    fun updateDailyQuota(quota: Int) {
        _uiState.update { currentState ->
            currentState.copy(dailyQuota = quota.coerceIn(1, 30))
        }
    }
}

/**
 * UI State for Social Tracker screen
 */
data class SocialTrackerUiState(
    val todayCount: Int = 0,
    val totalCount: Int = 0,
    val dailyQuota: Int = 1 // Default quota is 1
)

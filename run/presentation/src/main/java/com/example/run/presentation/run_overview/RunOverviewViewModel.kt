package com.example.run.presentation.run_overview

import androidx.lifecycle.ViewModel

class RunOverviewViewModel : ViewModel() {
    fun onAction(action: RunOverviewAction) {
        when (action) {
            is RunOverviewAction.OnStartClick -> {
                // Handle start click
            }

            is RunOverviewAction.OnLogoutClick -> {
                // Handle logout click
            }

            is RunOverviewAction.OnAnalyticsClick -> {
                // Handle analytics click
            }
        }
    }
}
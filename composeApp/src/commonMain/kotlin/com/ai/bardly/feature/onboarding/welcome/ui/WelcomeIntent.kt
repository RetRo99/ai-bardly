package com.ai.bardly.feature.onboarding.welcome.ui

import com.ai.bardly.base.ScreenIntent

sealed interface WelcomeIntent : ScreenIntent {
    data object OpenMain : WelcomeIntent
}
package com.ai.bardly.feature.onboarding.welcome.ui

import com.retro99.base.ui.BaseScreenIntent

sealed interface WelcomeIntent : BaseScreenIntent {
    data object OpenMain : WelcomeIntent
}
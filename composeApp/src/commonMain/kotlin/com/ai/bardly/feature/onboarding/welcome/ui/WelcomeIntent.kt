package com.ai.bardly.feature.onboarding.welcome.ui

import com.retro99.base.BaseScreenIntent

sealed interface WelcomeIntent : BaseScreenIntent {
    data object OpenMain : WelcomeIntent
}
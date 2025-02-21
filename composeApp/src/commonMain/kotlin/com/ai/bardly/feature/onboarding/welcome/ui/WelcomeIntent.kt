package com.ai.bardly.feature.onboarding.welcome.ui

import com.ai.bardly.base.BaseScreenIntent

sealed interface WelcomeIntent : BaseScreenIntent {
    data object OpenMain : WelcomeIntent
}
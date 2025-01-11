package com.ai.bardly.navigation

import com.ai.bardly.analytics.Analytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ScreenViewAnalytics : KoinComponent {
    protected val analytics by inject<Analytics>()
    abstract fun logScreenOpen()
}
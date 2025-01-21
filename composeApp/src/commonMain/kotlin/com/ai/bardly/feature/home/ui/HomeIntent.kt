package com.ai.bardly.feature.home.ui

import com.ai.bardly.base.ScreenIntent

sealed interface HomeIntent : ScreenIntent {
    data class OpenChatClicked(val gameTitle: String, val gameId: Int) : HomeIntent
}
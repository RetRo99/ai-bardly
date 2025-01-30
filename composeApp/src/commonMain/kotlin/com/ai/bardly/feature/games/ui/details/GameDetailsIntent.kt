package com.ai.bardly.feature.games.ui.details

import com.ai.bardly.base.ScreenIntent

sealed interface GameDetailsIntent : ScreenIntent {
    data object NavigateBack : GameDetailsIntent
    data object OpenChatClicked : GameDetailsIntent
}
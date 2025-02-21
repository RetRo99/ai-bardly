package com.ai.bardly.feature.main.games.ui.details

import com.ai.bardly.base.BaseScreenIntent

sealed interface GameDetailsIntent : BaseScreenIntent {
    data object NavigateBack : GameDetailsIntent
    data object OpenChatClicked : GameDetailsIntent
}
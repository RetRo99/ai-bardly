package com.ai.bardly.feature.main.games.ui.details

import com.retro99.base.BaseScreenIntent

sealed interface GameDetailsIntent : BaseScreenIntent {
    data object NavigateBack : GameDetailsIntent
    data object OpenChatClicked : GameDetailsIntent
}
package com.ai.bardly.feature.games.ui.details

import com.ai.bardly.base.ScreenIntent

sealed interface GameDetailsIntent : ScreenIntent {
    object NavigateBack : GameDetailsIntent
}
package com.ai.bardly.feature.home.ui

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.navigation.GeneralDestination

class HomeViewModel : BaseViewModel<HomeViewState, HomeIntent>() {

    override val defaultViewState = HomeViewState()
    override suspend fun handleScreenIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OpenChatClicked -> openChat(intent.gameTitle, intent.gameId)
        }
    }

    override val initialState = BaseViewState.Success(defaultViewState)

    private fun openChat(gameTitle: String, gameId: Int) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle, gameId))
    }
}
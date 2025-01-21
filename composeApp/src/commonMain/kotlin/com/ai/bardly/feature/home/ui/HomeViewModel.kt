package com.ai.bardly.feature.home.ui

import androidx.lifecycle.viewModelScope
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<HomeViewState, HomeIntent>() {

    override val defaultViewState = HomeViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        loadRecentGames()
    }

    override suspend fun handleScreenIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OpenChatClicked -> openChat(intent.gameTitle, intent.gameId)
        }
    }

    private fun openChat(gameTitle: String, gameId: Int) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle, gameId))
    }

    private fun loadRecentGames() {
        viewModelScope.launch {

        }
    }
}
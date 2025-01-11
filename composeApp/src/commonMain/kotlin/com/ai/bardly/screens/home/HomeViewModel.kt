package com.ai.bardly.screens.home

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.navigation.GeneralDestination

class HomeViewModel : BaseViewModel<BaseViewState<Unit>>() {

    fun onOpenChatClicked(gameTitle: String) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle))
    }
}
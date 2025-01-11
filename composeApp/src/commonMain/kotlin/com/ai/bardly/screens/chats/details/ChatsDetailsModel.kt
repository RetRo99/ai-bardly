package com.ai.bardly.screens.chats.details

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class ChatsDetailsModel : BaseViewModel<BaseViewState<ChatDetailsViewState>>() {

    fun onBackClick() {
        navigateBack()
    }
}
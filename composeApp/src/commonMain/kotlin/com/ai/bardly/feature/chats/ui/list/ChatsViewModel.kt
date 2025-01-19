package com.ai.bardly.feature.chats.ui.list

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class ChatsViewModel : BaseViewModel<Unit>() {
    override val defaultScreenData = Unit

    override val initialState = BaseViewState.Success(defaultScreenData)
}
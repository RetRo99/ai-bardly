package com.ai.bardly.feature.chats.ui.list

import androidx.lifecycle.viewModelScope
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ChatListViewModel : BaseViewModel<ChatListViewState, ChatListIntent>() {
    override val defaultViewState = ChatListViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: ChatListIntent) {
        when (intent) {
            is ChatListIntent.Logout -> {
                Firebase.auth.signOut()
            }
        }
    }

    override fun onScreenDisplayed() {
        viewModelScope.launch {
            Firebase.auth.authStateChanged.collect { result ->
                val token = result?.getIdToken(false)
                updateOrSetSuccess {
                    it.copy(
                        userToken = token
                    )
                }
            }
        }
    }
}
package com.ai.bardly.feature.chats.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher

@Composable
fun ChatsListScreen(
) {
    BaseScreen<ChatListViewModel, ChatListViewState, ChatListIntent> { viewState, intentDispatcher ->
        ChatsListContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun ChatsListContent(
    viewState: ChatListViewState,
    intentDispatcher: IntentDispatcher<ChatListIntent>,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
    }
}
package com.ai.bardly.feature.chats.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.base.BaseScreen

@Composable
fun ChatsListScreen(
) {
    BaseScreen<ChatListViewModel, Unit, ChatListIntent> { viewState, intentDispatcher ->
        ChatsListContent()
    }
}

@Composable
private fun ChatsListContent() {
    Box(Modifier.fillMaxSize()) {
        Text("Chats")
    }
}
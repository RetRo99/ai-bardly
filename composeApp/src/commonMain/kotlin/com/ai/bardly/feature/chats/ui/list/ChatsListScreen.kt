package com.ai.bardly.feature.chats.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.ui.BaseScreen

@Composable
fun ChatsListScreen(
) {
    BaseScreen<ChatsViewModel, Unit> { viewMode, viewState ->
        ChatsListContent()
    }
}

@Composable
private fun ChatsListContent() {
    Box(Modifier.fillMaxSize()) {
        Text("Chats")
    }
}
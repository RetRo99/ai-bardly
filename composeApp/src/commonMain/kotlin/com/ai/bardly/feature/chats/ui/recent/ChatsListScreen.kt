package com.ai.bardly.feature.chats.ui.recent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsListScreen(
) {
    koinViewModel<ChatsViewModel>()
    Box(Modifier.fillMaxSize()) {
        Text("CHATS")
    }
}
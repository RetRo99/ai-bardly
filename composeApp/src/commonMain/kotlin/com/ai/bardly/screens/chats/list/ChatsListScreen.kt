package com.ai.bardly.screens.chats.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatsListScreen(
) {
    val viewModel = koinViewModel<ChatsViewModel>()
    EmptyScreenContent(Modifier.fillMaxSize(), "CHATS")
}
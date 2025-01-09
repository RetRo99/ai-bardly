package com.ai.bardly.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
) {
    val viewModel = koinViewModel<HomeViewModel>()

    EmptyScreenContent(Modifier.fillMaxSize(), "HOME")
}
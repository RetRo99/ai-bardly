package com.ai.bardly.screens.chats.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ai.bardly.base.BaseViewState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ChatDetailsScreen(
    gameTitle: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: ChatsDetailsModel = koinViewModel { parametersOf(gameTitle) }
    val viewState = viewModel.viewState.collectAsState()
    ChatDetailsScreenContent(
        state = viewState,
        onBackClick = viewModel::onBackClick,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ChatDetailsScreenContent(
    state: State<BaseViewState<ChatDetailsViewState>>,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val viewState = state.value) {
            is BaseViewState.Loading -> {
                // Loading state
            }

            is BaseViewState.Error -> {
                // Error state
            }

            is BaseViewState.Loaded -> {
                ChatDetails(
                    onBackClick = onBackClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ChatDetails(
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Chat details content
        Text(   // Replace this with the actual content
            text = "Chat details",
            modifier = Modifier.fillMaxSize()
        )
    }
}
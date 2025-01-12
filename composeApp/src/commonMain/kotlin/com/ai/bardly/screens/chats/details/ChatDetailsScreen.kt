package com.ai.bardly.screens.chats.details

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.ic_send
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ChatDetailsScreen(
    gameTitle: String,
    gameId: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: ChatsDetailsViewModel = koinViewModel { parametersOf(gameTitle, gameId) }
    val viewState = viewModel.viewState.collectAsState()
    ChatDetailsScreenContent(
        state = viewState,
        onBackClick = viewModel::onBackClick,
        animatedVisibilityScope = animatedVisibilityScope,
        onMessageSendClicked = viewModel::onMessageSendClicked,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ChatDetailsScreenContent(
    state: State<BaseViewState<ChatDetailsViewState>>,
    onBackClick: () -> Unit,
    onMessageSendClicked: (String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
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
                    title = viewState.data.title,
                    id = viewState.data.gameId,
                    messages = viewState.data.messages,
                    onBackClick = onBackClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onMessageSendClicked = onMessageSendClicked,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ChatDetails(
    title: String,
    id: Int,
    messages: List<MessageUiModel>,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onMessageSendClicked: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            // Top Bar with Back Button
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),  // Compensate for IconButton width
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(8.dp).sharedBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            sharedContentState = rememberSharedContentState(
                                key = "$id title",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }

            MessageInputField(
                modifier = Modifier.padding(16.dp),
                onMessageSendClicked = onMessageSendClicked
            )
        }
    }
}

@Composable
private fun MessageBubble(message: MessageUiModel) {
    val textAlignment = if (message is MessageUiModel.UserMessage) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }
    val shape = if (message is MessageUiModel.UserMessage) {
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)
    } else {
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp)
    }

    val color = if (message is MessageUiModel.UserMessage) {
        Color(0xFFA55E5D)
    } else {
        Color(0xFF56479C)
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = textAlignment
    ) {
        Box(
            modifier = Modifier
                .background(color, shape = shape),
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun MessageInputField(
    modifier: Modifier = Modifier,
    onMessageSendClicked: (String) -> Unit,
) {
    val state = rememberTextFieldState()
    Surface(
        modifier = modifier
            .imePadding()
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEBECEF)),
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .defaultMinSize(
                    minWidth = OutlinedTextFieldDefaults.MinWidth,
                    minHeight = OutlinedTextFieldDefaults.MinHeight
                ),
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (state.text.isEmpty()) {
                            Text(
                                text = "Ask Bardly...",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray // Placeholder color
                                )
                            )
                        }
                        innerTextField()
                    }
                    AnimatedVisibility(
                        visible = state.text.isNotBlank(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(
                            onClick = {
                                onMessageSendClicked(state.text.toString())
                                state.clearText()
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFFA55E5D)
                            )
                        ) {
                            Image(painterResource(Res.drawable.ic_send), contentDescription = null)
                        }
                    }
                }
            }
        )
    }
}
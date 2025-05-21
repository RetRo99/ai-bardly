package com.bardly.chats.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bardly.chats.ui.model.MessageUiModel
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.sharedScreenBounds
import com.retro99.base.ui.resources.DrawableRes
import com.retro99.translations.StringRes
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import resources.icons.ic_send
import resources.translations.chat_details_message_hint

@Composable
fun ChatScreen(
    component: ChatPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        ChatScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun ChatScreenContent(
    viewState: ChatViewState,
    intentDispatcher: IntentDispatcher<ChatScreenIntent>,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Chat(
            title = viewState.title,
            id = viewState.gameId,
            messages = viewState.messages,
            isResponding = viewState.isResponding,
            intentDispatcher = intentDispatcher,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Chat(
    title: String,
    id: String,
    messages: List<MessageUiModel>,
    isResponding: Boolean,
    intentDispatcher: IntentDispatcher<ChatScreenIntent>,
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
                IconButton(onClick = { intentDispatcher(ChatScreenIntent.NavigateBack) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),  // Compensate for IconButton width
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(8.dp).sharedScreenBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            key = "$id title",
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
                if (isResponding) {
                    item {
                        BardlyThinkingDots(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        )
                    }
                }
                items(messages) { message ->
                    Column {
                        MessageBubble(
                            onAnimationEnded = {
                                intentDispatcher(ChatScreenIntent.MessageAnimationDone(message))
                            },
                            text = message.question,
                            isQuestion = true,
                            animateText = false,
                        )
                        message.answer?.let {
                            Spacer(modifier = Modifier.height(16.dp))

                            MessageBubble(
                                text = it,
                                isQuestion = false,
                                animateText = message.animateText,
                                onAnimationEnded = {
                                    intentDispatcher(ChatScreenIntent.MessageAnimationDone(message))
                                },
                            )
                        }
                    }
                }
            }

            MessageInputField(
                modifier = Modifier.padding(16.dp),
                onMessageSendClicked = {
                    intentDispatcher(ChatScreenIntent.SendMessage(it))
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.MessageBubble(
    text: String,
    isQuestion: Boolean,
    animateText: Boolean,
    onAnimationEnded: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textAlignment = if (isQuestion) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }
    val shape = if (isQuestion) {
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)
    } else {
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp)
    }

    val color = if (isQuestion) {
        Color(0xFFA55E5D)
    } else {
        Color(0xFF56479C)
    }
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = textAlignment
    ) {
        Box(
            modifier = Modifier
                .background(color, shape = shape)
                .animateItem(),
        ) {
            var animatedText by remember(text) {
                mutableStateOf(if (animateText) "" else text)
            }

            if (animateText) {
                LaunchedEffect(Unit) {
                    text.indices.forEach { index ->
                        delay(20)
                        animatedText = text.substring(0..index)
                    }
                    onAnimationEnded()
                }
            }
            val clipboardManager: ClipboardManager = LocalClipboardManager.current

            SelectionContainer {
                Markdown(
                    content = animatedText,
                    colors = markdownColor(
                        text = Color.White,
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .combinedClickable(
                            onClick = {}, // You can add click handling if needed
                            onLongClick = {
                                clipboardManager.setText(AnnotatedString(text))
                            }
                        ),
                )
            }
        }
    }
}

@Composable
fun MessageInputField(
    modifier: Modifier = Modifier,
    onMessageSendClicked: (String) -> Unit,
) {
    val state = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
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
                )
                .focusRequester(focusRequester),
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
                                text = stringResource(StringRes.chat_details_message_hint),
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
                            Image(
                                painterResource(DrawableRes.ic_send),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun BardlyThinkingDots(modifier: Modifier) {
    val delayUnit = 300
    val maxOffset = 10f

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(4.dp)
            .offset(y = -offset.dp)
            .background(
                color = Color(0xFF56479C),
                shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay using LinearEasing
                maxOffset at delay + delayUnit using LinearEasing
                0f at delay + delayUnit * 2
            }
        )
    )

    val offset1 by animateOffsetWithDelay(0)
    val offset2 by animateOffsetWithDelay(delayUnit)
    val offset3 by animateOffsetWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 12.dp),
    ) {
        val spaceSize = 4.dp
        Spacer(modifier = Modifier.width(12.dp))
        Dot(offset1)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3)
    }
}

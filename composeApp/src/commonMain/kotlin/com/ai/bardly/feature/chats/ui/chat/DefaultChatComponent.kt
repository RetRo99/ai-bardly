package com.ai.bardly.feature.chats.ui.chat

import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.model.MessageType
import com.ai.bardly.feature.chats.ui.model.MessageUiModel
import com.ai.bardly.feature.chats.ui.model.toDomainModel
import com.ai.bardly.feature.chats.ui.model.toUiModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch

class DefaultChatComponent(
    componentContext: ComponentContext,
    private val gameTitle: String,
    private val gameId: Int,
    private val chatsRepository: ChatsRepository,
    val navigateBack: () -> Unit,
) : BaseComponentImpl<ChatViewState, ChatScreenIntent>(componentContext), ChatComponent {

    private var questionsAskedInSession = 0

    override val defaultViewState = ChatViewState(
        title = gameTitle,
        gameId = gameId,
        messages = emptyList(),
        isResponding = false
    )

    override suspend fun handleScreenIntent(intent: ChatScreenIntent) {
        when (intent) {
            ChatScreenIntent.NavigateBack -> navigateBack()
            is ChatScreenIntent.MessageAnimationDone -> onMessageAnimationEnded(intent.message)
            is ChatScreenIntent.SendMessage -> onMessageSendClicked(intent.messageText)
        }
    }

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        scope.launch {
            chatsRepository
                .getMessages(gameId)
                .onSuccess { messages ->
                    updateOrSetSuccess {
                        it.copy(
                            messages = messages.map { it.toUiModel(false) }
                        )
                    }
                }
                .onFailure {
                    setError(throwable = it)
                }
        }
    }

    private suspend fun onMessageSendClicked(messageText: String) {
        val message = displayAndGetMessage(
            messageText = messageText,
            id = gameId,
        )
        chatsRepository
            .getAnswerFor(message.toDomainModel())
            .onSuccess { answer ->
                displayMessage(answer.toUiModel(true))
            }.onFailure {
                updateOrSetSuccess {
                    it.copy(
                        isResponding = false
                    )
                }
            }
    }

    private fun onMessageAnimationEnded(message: MessageUiModel) {
        updateOrSetSuccess {
            it.copy(
                messages = it.messages.map {
                    if (it.timestamp == message.timestamp) {
                        it.copy(animateText = false)
                    } else {
                        it
                    }
                }
            )
        }
    }

    private fun displayAndGetMessage(
        messageText: String,
        id: Int,
    ): MessageUiModel {
        val message = MessageUiModel(messageText, MessageType.User, id, gameTitle)
        displayMessage(message)
        return message
    }

    private fun displayMessage(
        message: MessageUiModel
    ) {
        updateOrSetSuccess {
            it.copy(
                messages = listOf(message) + it.messages,
                isResponding = message.isUserMessage,
            )
        }
    }

    override fun onDestroy() {
        analytics.log(AnalyticsEvent.QuestionsAsked(gameTitle, questionsAskedInSession))
    }
}
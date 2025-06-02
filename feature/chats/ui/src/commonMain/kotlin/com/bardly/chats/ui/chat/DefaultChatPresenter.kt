package com.bardly.chats.ui.chat

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.chats.ui.model.MessageUiModel
import com.bardly.chats.ui.model.toDomainModel
import com.bardly.chats.ui.model.toUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.base.ui.compose.TextWrapper
import com.retro99.chats.domain.ChatsRepository
import com.retro99.snackbar.api.SnackbarManager
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias ChatPresenterFactory = (
    ComponentContext,
    gameTitle: String,
    gameId: String,
    navigateBack: () -> Unit,
) -> DefaultChatPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = ChatPresenter::class)
class DefaultChatPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val gameTitle: String,
    @Assisted private val gameId: String,
    @Assisted private val navigateBack: () -> Unit,
    private val chatsRepository: ChatsRepository,
    private val analytics: Analytics,
    private val snackbarManager: SnackbarManager,
) : BasePresenterImpl<ChatViewState, ChatScreenIntent>(componentContext), ChatPresenter {

    private var questionsAskedInSession = 0

    override val defaultViewState = ChatViewState(
        title = gameTitle,
        gameId = gameId,
        messages = emptyList(),
        isResponding = false
    )

    override fun handleScreenIntent(intent: ChatScreenIntent) {
        when (intent) {
            ChatScreenIntent.NavigateBack -> navigateBack()
            is ChatScreenIntent.MessageAnimationDone -> onMessageAnimationEnded(intent.message)
            is ChatScreenIntent.SendMessage -> onMessageSendClicked(intent.messageText)
        }
    }

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        launchDataOperation(
            block = {
                chatsRepository
                    .getMessages(gameId)
            }
        ) { messages ->
            updateOrSetSuccess {
                it.copy(
                    messages = messages.map { it.toUiModel() }
                )
            }
        }
    }

    private fun onMessageSendClicked(messageText: String) {
        val message = displayAndGetMessage(
            messageText = messageText,
            id = gameId,
        )

        launchDataOperation(
            block = {
                chatsRepository
                    .getAnswerFor(message.toDomainModel())
            },
            onError = {
                snackbarManager.showSnackbar(TextWrapper.Text("Error: ${it.message}"))
                updateOrSetSuccess {
                    it.copy(
                        isResponding = false
                    )
                }
            }
        ) { answer ->
            updateOrSetSuccess {
                it.copy(
                    messages = it.messages.map {
                        if (it.timestamp == message.timestamp) {
                            it.copy(answer = answer.answer, animateText = true)
                        } else {
                            it
                        }
                    },
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
        id: String,
    ): MessageUiModel {
        val message = MessageUiModel(
            question = messageText,
            answer = null,
            gameId = id,
            gameTitle = gameTitle,
        )
        displayMessage(message)
        return message
    }

    private fun displayMessage(
        message: MessageUiModel
    ) {
        updateOrSetSuccess {
            it.copy(
                messages = listOf(message) + it.messages,
                isResponding = message.answer == null,
            )
        }
    }

    override fun onDestroy() {
        analytics.log(AnalyticsEvent.QuestionsAsked(gameTitle, questionsAskedInSession))
    }
}

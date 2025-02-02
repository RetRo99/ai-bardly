package com.ai.bardly.feature.chats.ui.list

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chat_lists_recent_chats
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.chats.ui.model.RecentMessageUiModel
import com.ai.bardly.feature.games.ui.components.GameImage
import com.ai.bardly.util.timeAgo
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatsListScreen(
) {
    BaseScreen<ChatListViewModel, ChatListViewState, ChatListIntent> { viewState, intentDispatcher ->
        ChatsListContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatsListContent(
    viewState: ChatListViewState,
    intentDispatcher: IntentDispatcher<ChatListIntent>,
) {
    LazyColumn {
        stickyHeader {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp, // Adjust elevation as needed
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = stringResource(Res.string.chat_lists_recent_chats),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
        items(viewState.recentChats.size) { index ->
            val recentChat = viewState.recentChats[index]
            RecentChatItem(
                chat = recentChat,
                recentChatClicked = {
                    intentDispatcher(
                        ChatListIntent.RecentChatClicked(
                            recentChat.gameTitle,
                            gameId = recentChat.gameId
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun RecentChatItem(
    chat: RecentMessageUiModel,
    recentChatClicked: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.clickable { recentChatClicked() }.padding(12.dp).fillMaxWidth(),
        ) {
            GameImage(
                imageUrl = chat.thumbnail,
                gameId = chat.gameId,
                size = 56.dp
            )
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = chat.gameTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chat.timestamp.timeAgo(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

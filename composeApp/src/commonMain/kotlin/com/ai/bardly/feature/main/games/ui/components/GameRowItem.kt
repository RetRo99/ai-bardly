package com.ai.bardly.feature.main.games.ui.components

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.ic_chat
import ai_bardly.composeapp.generated.resources.ic_clock
import ai_bardly.composeapp.generated.resources.ic_players
import ai_bardly.composeapp.generated.resources.ic_rating
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import org.jetbrains.compose.resources.painterResource


@Composable
fun GameRowItem(
    game: GameUiModel,
    onClick: (GameUiModel) -> Unit,
    onChatClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .clickable { onClick(game) }
                .padding(12.dp)
        ) {
            GameImage(
                imageUrl = game.thumbnail,
                size = 90.dp,
                gameId = game.id,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp)
            ) {
                TitleAndChatRow(
                    gameId = game.id,
                    gameTitle = game.title,
                    onChatClick = onChatClick,
                )
                Spacer(modifier = Modifier.weight(1f))
                GameAttributesRow(
                    gameId = game.id,
                    numberOfPlayers = game.numberOfPlayers,
                    playingTime = game.playingTime,
                    rating = game.rating,
                )
            }

        }
    }
}

@Composable
private fun GameAttributesRow(
    gameId: Int,
    numberOfPlayers: String,
    playingTime: String,
    rating: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SharedTransitionText(
            key = "$gameId numberOfPlayers",
            iconRes = Res.drawable.ic_players,
            text = numberOfPlayers,
        )

        SharedTransitionText(
            key = "$gameId playingTime",
            iconRes = Res.drawable.ic_clock,
            text = playingTime,
        )
        SharedTransitionText(
            key = "$gameId rating",
            text = rating,
            iconRes = Res.drawable.ic_rating,
        )
    }
}

@Composable
private fun TitleAndChatRow(
    gameId: Int,
    gameTitle: String,
    onChatClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        SharedTransitionText(
            key = "$gameId title",
            text = gameTitle,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.ic_chat),
            contentDescription = null,
            modifier = Modifier
                .clickable { onChatClick(gameTitle, gameId) }
                .padding(end = 4.dp),
        )
    }
}

@Composable
fun GamesLazyColumn(
    itemCount: () -> Int,
    getItem: (Int) -> GameUiModel?,
    getKey: (Int) -> Any,
    onGameClicked: (GameUiModel) -> Unit,
    onOpenChatClicked: (String, Int) -> Unit,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = itemCount(),
            key = getKey,
            contentType = { "GameCard" }
        ) { index ->
            val game = getItem(index)
            if (game != null) {
                GameRowItem(
                    game = game,
                    onClick = onGameClicked,
                    onChatClick = onOpenChatClicked
                )
            }
        }
    }
}
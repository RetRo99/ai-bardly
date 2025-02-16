package com.ai.bardly.feature.main.games.ui.components

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.ic_chat
import ai_bardly.composeapp.generated.resources.ic_clock
import ai_bardly.composeapp.generated.resources.ic_players
import ai_bardly.composeapp.generated.resources.ic_rating
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.ai.bardly.ui.CoilImage
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameCard(
    game: GameUiModel,
    onClick: (GameUiModel) -> Unit,
    onChatClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    with(LocalScreenTransitionScope.current) {
        Card(
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(game.id),
                    renderInOverlayDuringTransition = false,
                    animatedVisibilityScope = LocalScreenAnimationScope.current
                )
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.clickable { onClick(game) }.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row {
                    GameImage(
                        imageUrl = game.thumbnail,
                        gameId = game.id,
                        size = 80.dp
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(Res.drawable.ic_chat),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onChatClick(game.title, game.id) }
                            .padding(end = 4.dp),
                    )
                }


                SharedTransitionText(
                    key = "${game.id} title",
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SharedTransitionText(
                        key = "${game.id} numberOfPlayers",
                        iconRes = Res.drawable.ic_players,
                        text = game.numberOfPlayers,
                    )
                    SharedTransitionText(
                        key = "${game.id} rating",
                        text = game.rating,
                        iconRes = Res.drawable.ic_rating,
                        textAlign = TextAlign.Center
                    )
                }

                SharedTransitionText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    key = "${game.id} playingTime",
                    iconRes = Res.drawable.ic_clock,
                    text = game.playingTime,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionText(
    key: String,
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
    iconRes: DrawableResource? = null,
) {
    with(LocalScreenTransitionScope.current) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.sharedBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                sharedContentState = rememberSharedContentState(key),
                renderInOverlayDuringTransition = false,
                animatedVisibilityScope = LocalScreenAnimationScope.current
            )
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp),
                )
            }

            Text(
                text = text,
                style = style,
                textAlign = textAlign
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameImage(
    imageUrl: String,
    gameId: Int,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    with(LocalScreenTransitionScope.current) {
        Card(
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "$gameId thumbnail",
                    ),
                    animatedVisibilityScope = LocalScreenAnimationScope.current,
                    renderInOverlayDuringTransition = false,
                ),
            shape = RoundedCornerShape(8.dp),
        ) {
            CoilImage(
                data = imageUrl,
                cacheKey = imageUrl,
                modifier = Modifier.size(size),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
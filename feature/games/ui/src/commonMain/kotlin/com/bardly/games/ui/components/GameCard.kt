package com.bardly.games.ui.components

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
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.compose.CoilImage
import com.retro99.base.ui.compose.sharedScreenBounds
import com.retro99.base.ui.resources.DrawableRes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import resources.icons.ic_chat
import resources.icons.ic_clock
import resources.icons.ic_players
import resources.icons.ic_rating

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameCard(
    game: GameUiModel,
    onClick: (GameUiModel) -> Unit,
    onChatClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .sharedScreenBounds(
                key = game.id,
                renderInOverlayDuringTransition = false,
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
                    painter = painterResource(DrawableRes.ic_chat),
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
                    iconRes = DrawableRes.ic_players,
                    text = game.numberOfPlayers,
                )
                SharedTransitionText(
                    key = "${game.id} rating",
                    text = game.rating,
                    iconRes = DrawableRes.ic_rating,
                    textAlign = TextAlign.Center
                )
            }

            SharedTransitionText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                key = "${game.id} playingTime",
                iconRes = DrawableRes.ic_clock,
                text = game.playingTime,
            )
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.sharedScreenBounds(
            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
            key = key,
            renderInOverlayDuringTransition = false,
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameImage(
    imageUrl: String,
    gameId: String,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .sharedScreenBounds(
                key = "$gameId thumbnail",
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
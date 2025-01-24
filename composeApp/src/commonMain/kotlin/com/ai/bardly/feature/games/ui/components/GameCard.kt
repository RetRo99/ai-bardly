package com.ai.bardly.feature.games.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.ui.CoilImage
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameCard(
    game: GameUiModel,
    onGameClicked: (GameUiModel) -> Unit,
    onOpenChatClicked: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    with(LocalScreenTransitionScope.current) {
        Card(
            modifier = modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = game.id,
                ),
                renderInOverlayDuringTransition = false,
                animatedVisibilityScope = LocalScreenAnimationScope.current
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.clickable(onClick = { onGameClicked(game) }).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Card(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ).sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "${game.id} thumbnail",
                            ),
                            animatedVisibilityScope = LocalScreenAnimationScope.current,
                            renderInOverlayDuringTransition = false,
                        ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    CoilImage(
                        data = game.thumbnail,
                        cacheKey = game.thumbnail,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Text(
                    modifier = Modifier.sharedBounds(
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                        sharedContentState = rememberSharedContentState(
                            key = "${game.id} title",
                        ),
                        renderInOverlayDuringTransition = false,
                        animatedVisibilityScope = LocalScreenAnimationScope.current,
                    ),
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    modifier = Modifier.sharedBounds(
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                        sharedContentState = rememberSharedContentState(
                            key = "${game.id} rating",
                        ),
                        renderInOverlayDuringTransition = false,
                        animatedVisibilityScope = LocalScreenAnimationScope.current
                    ),
                    text = "⭐ ${game.rating}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.sharedBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            sharedContentState = rememberSharedContentState(
                                key = "${game.id} numberOfPlayers",
                            ),
                            renderInOverlayDuringTransition = false,
                            animatedVisibilityScope = LocalScreenAnimationScope.current
                        ),
                        text = "\uD83D\uDC65 ${game.numberOfPlayers}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.sharedBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            sharedContentState = rememberSharedContentState(
                                key = "${game.id} playingTime",
                            ),
                            renderInOverlayDuringTransition = false,
                            animatedVisibilityScope = LocalScreenAnimationScope.current
                        ),
                        text = "\uD83D\uDD52 ${game.playingTime}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onOpenChatClicked(game.title, game.id) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "See Chat")
                }
            }
        }
    }
}
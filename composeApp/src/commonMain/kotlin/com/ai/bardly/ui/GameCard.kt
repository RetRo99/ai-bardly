package com.ai.bardly.ui

import androidx.compose.animation.AnimatedVisibilityScope
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
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ai.bardly.GameUiModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GameCard(
    game: GameUiModel,
    onGameClicked: (GameUiModel) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                    ).sharedElement(
                        state = rememberSharedContentState(
                            key = "${game.id} thumbnail",
                        ), animatedVisibilityScope
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(game.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(
                        key = "${game.id} title",
                    ), animatedVisibilityScope
                ).skipToLookaheadSize(),
                text = game.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(
                        key = "${game.id} rating",
                    ), animatedVisibilityScope
                ).skipToLookaheadSize(),
                text = "⭐ ${game.rating}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(
                            key = "${game.id} numberOfPlayers",
                        ), animatedVisibilityScope
                    ).skipToLookaheadSize(),
                    text = "\uD83D\uDC65 ${game.numberOfPlayers}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(
                            key = "${game.id} playingTime",
                        ), animatedVisibilityScope
                    ).skipToLookaheadSize(),
                    text = "\uD83D\uDD52 ${game.playingTime}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = { /* TODO: Add navigation or action */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "See Chat")
            }
        }
    }
}
package com.bardly.games.ui.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bardly.games.ui.components.GameImage
import com.bardly.games.ui.components.SharedTransitionText
import com.bardly.games.ui.model.GameUiModel
import com.mikepenz.markdown.m3.Markdown
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.sharedScreenBounds
import com.retro99.base.ui.resources.DrawableRes
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import resources.icons.ic_chat
import resources.icons.ic_rating
import resources.translations.amount_of_players
import resources.translations.game_age_recommendation
import resources.translations.game_categories
import resources.translations.game_complexity
import resources.translations.game_length
import resources.translations.game_types

@Composable
fun GameDetailsScreen(
    component: GameDetailsPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        GamesScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
fun GamesScreenContent(
    viewState: GameDetailsViewState,
    intentDispatcher: IntentDispatcher<GameDetailsIntent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            onBackClick = { intentDispatcher(GameDetailsIntent.NavigateBack) },
            onChangeFavorite = { new ->
                intentDispatcher(
                    GameDetailsIntent.OnChangeFavoriteClicked(
                        new
                    )
                )
            },
            isFavorite = viewState.isFavorite,
        )

        GameDetailsContent(
            game = viewState.game,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    onChangeFavorite: (Boolean) -> Unit,
    isFavorite: Boolean?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        if (isFavorite != null) {
            AnimatedContent(
                targetState = isFavorite,
                transitionSpec = {
                    if (targetState) {
                        scaleIn(initialScale = 0.8f) + fadeIn() togetherWith
                                scaleOut(targetScale = 2f) + fadeOut()
                    } else {
                        fadeIn() togetherWith fadeOut()
                    }
                }
            ) { isFavorite ->
                IconButton(
                    onClick = { onChangeFavorite(!isFavorite) }
                ) {
                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Remove from favorites",
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add to favorites",
                            tint = LocalContentColor.current
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameDetailsContent(
    game: GameUiModel,
    intentDispatcher: IntentDispatcher<GameDetailsIntent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GameImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentSize(),
            imageUrl = game.thumbnail,
            gameId = game.id,
            size = 180.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameHeaderSection(
            rating = game.rating,
            yearPublished = game.yearPublished,
            title = game.title,
            id = game.id,
            onChatClicked = { intentDispatcher(GameDetailsIntent.OpenChatClicked) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameDescriptionSection(game = game)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LabelSection(
    title: StringResource,
    labels: List<String>
) {
    Column {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(labels.size) { index ->
                val type = labels[index]
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GameHeaderSection(
    rating: String,
    yearPublished: String,
    title: String,
    id: Int,
    onChatClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        TitleAndRating(
            rating = rating, yearPublished = yearPublished, title = title, id = id,
        )
        Spacer(modifier = Modifier.weight(1f))
        OpenChatButton(onChatClicked)
    }
}

@Composable
private fun OpenChatButton(
    onChatClicked: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        onClick = onChatClicked,
    ) {
        Icon(
            painter = painterResource(DrawableRes.ic_chat),
            contentDescription = null,
        )
    }
}

@Composable
private fun TitleAndRating(
    rating: String,
    yearPublished: String,
    title: String,
    id: Int
) {
    Column {
        SharedTransitionText(
            key = "$id title",
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Row {
            SharedTransitionText(
                key = "$id year",
                text = yearPublished,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "|",
                style = MaterialTheme.typography.bodyMedium
            )

            SharedTransitionText(
                key = "$id rating",
                iconRes = DrawableRes.ic_rating,
                text = rating,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun GameDescriptionSection(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium
        )

        GameInformationCards(game = game)

        game.types?.let {
            LabelSection(
                title = StringRes.game_types,
                labels = it,
            )
        }

        game.categories?.let {
            LabelSection(
                title = StringRes.game_categories,
                labels = it,
            )
        }

        Description(game.description)
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun GameInformationCards(
    game: GameUiModel,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameInfoCard(
            modifier = Modifier.sharedScreenBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                key = "${game.id} numberOfPlayers",
            ),
            label = StringRes.amount_of_players,
            value = game.numberOfPlayers
        )
        GameInfoCard(
            modifier = Modifier.sharedScreenBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                key = "${game.id} playingTime",
            ),
            label = StringRes.game_length,
            value = game.playingTime
        )
        GameInfoCard(
            modifier = Modifier.sharedScreenBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                key = "${game.id} ageRange",
            ),
            label = StringRes.game_age_recommendation,
            value = game.ageRange
        )
        GameInfoCard(
            modifier = Modifier.sharedScreenBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                key = "${game.id} complexity",
            ),
            label = StringRes.game_complexity,
            value = "${game.complexity}/5"
        )
    }
}

@Composable
private fun Description(description: String) {
    Markdown(
        content = description,
    )
}

@Composable
fun GameInfoCard(
    label: StringResource,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier,
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(label),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

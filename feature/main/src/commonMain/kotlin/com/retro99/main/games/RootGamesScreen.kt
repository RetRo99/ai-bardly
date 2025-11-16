package com.retro99.main.games

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.bardly.games.ui.details.GameDetailsRootScreen
import com.bardly.games.ui.list.GamesListScreen
import com.retro99.base.ui.compose.LocalScreenTransitionScope

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun RootGamesScreen(
    component: RootGamesPresenter,
) {
    // Always show GamesListScreen
    val childSlot by component.childSlot.subscribeAsState()

    // Use the GamesListComponent from the RootGamesPresenter

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalScreenTransitionScope provides this
        ) {
            GamesListScreen(component.gamesListComponent)

            val gameDetails = childSlot.child?.instance as? RootGamesPresenter.Child.RootGameDetails

            gameDetails?.let {
                ModalBottomSheet(
                    onDismissRequest = { },
                    dragHandle = null,
                    sheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = true,
                    ),
                ) {
                    GameDetailsRootScreen(it.component)
                }
            }
        }
    }
}

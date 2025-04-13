package com.retro99.main.shelfs

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.shelfs.ui.details.ShelfDetailsScreen
import com.bardly.shelfs.ui.list.ShelfsListScreen
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootShelfsScreen(
    component: RootShelfsPresenter,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is RootShelfsPresenter.Child.ShelfsList -> {
                ShelfsListScreen(screen.component)
            }

            is RootShelfsPresenter.Child.ShelfDetails -> {
                ShelfDetailsScreen(screen.component)
            }
        }
    }
}
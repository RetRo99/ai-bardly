package com.retro99.main.shelfs

import com.bardly.shelfs.ui.details.ShelfDetailsPresenter
import com.bardly.shelfs.ui.list.ShelfsListComponent
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootShelfsPresenter : BasePresenter<RootShelfsViewState, RootShelfsIntent>,
    RootDecomposeComponent<RootShelfsPresenter.Child, RootShelfsPresenter.Config> {

    sealed interface Child {
        data class ShelfsList(val component: ShelfsListComponent) : Child
        data class ShelfDetails(val component: ShelfDetailsPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object ShelfsList : Config

        @Serializable
        data class ShelfDetails(val shelf: ShelfUiModel) : Config

    }
}

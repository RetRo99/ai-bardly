package com.bardly.shelfs.ui.details

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.shelfs.ui.model.toUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.shelfs.domain.ShelfsRepository
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias ShelfDetailsPresenterFactory = (
    componentContext: ComponentContext,
    shelf: ShelfUiModel,
    navigateBack: () -> Unit,
) -> DefaultShelfDetailsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = ShelfDetailsPresenter::class)
class DefaultShelfDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val shelf: ShelfUiModel,
    @Assisted private val navigateBack: () -> Unit,
    private val shelfsRepository: ShelfsRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<ShelfDetailsViewState, ShelfDetailsIntent>(componentContext),
    ShelfDetailsPresenter {

    override val defaultViewState = ShelfDetailsViewState(shelf)

    override val initialState = BaseViewState.Loading

    init {
        fetchShelf()
    }

    private fun fetchShelf() {
        launchOperation(
            block = { shelfsRepository.getShelf(shelf.id) },
            onSuccess = { shelfDomainModel ->
                updateOrSetSuccess { _ ->
                    ShelfDetailsViewState(shelfDomainModel.toUiModel())
                }
            }
        )
    }

    override fun handleScreenIntent(intent: ShelfDetailsIntent) {
        when (intent) {
            ShelfDetailsIntent.NavigateBack -> navigateBack()
        }
    }
}

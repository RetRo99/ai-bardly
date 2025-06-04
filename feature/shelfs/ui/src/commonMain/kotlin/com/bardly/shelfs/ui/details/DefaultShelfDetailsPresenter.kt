package com.bardly.shelfs.ui.details

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.shelfs.ui.model.toUiModel
import com.bardly.games.ui.model.GameUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.analytics.api.AnalyticsEventOrigin
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.base.ui.compose.TextWrapper
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.snackbar.api.SnackbarManager
import com.retro99.translations.StringRes
import resources.translations.shelf_details_failed_to_delete
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias ShelfDetailsPresenterFactory = (
    componentContext: ComponentContext,
    shelf: ShelfUiModel,
    navigateBack: () -> Unit,
    openGameDetails: (GameUiModel) -> Unit,
) -> DefaultShelfDetailsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = ShelfDetailsPresenter::class)
class DefaultShelfDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val shelf: ShelfUiModel,
    @Assisted private val navigateBack: () -> Unit,
    @Assisted private val openGameDetails: (GameUiModel) -> Unit,
    private val shelfsRepository: ShelfsRepository,
    private val analytics: Analytics,
    private val snackbarManager: SnackbarManager,
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
            is ShelfDetailsIntent.GameClicked -> handleGameClicked(intent)
            ShelfDetailsIntent.ShowDeleteConfirmationDialog -> showDeleteConfirmationDialog()
            ShelfDetailsIntent.HideDeleteConfirmationDialog -> hideDeleteConfirmationDialog()
            ShelfDetailsIntent.ConfirmDeleteShelf -> handleDeleteShelf()
        }
    }

    private fun showDeleteConfirmationDialog() {
        updateOrSetSuccess { it.copy(isDeleteConfirmationDialogVisible = true) }
    }

    private fun hideDeleteConfirmationDialog() {
        updateOrSetSuccess { it.copy(isDeleteConfirmationDialogVisible = false) }
    }

    private fun handleDeleteShelf() {
        launchDataOperation(
            block = { shelfsRepository.deleteShelf(shelf.id) },
            onError = { error ->
                snackbarManager.showSnackbar(
                    TextWrapper.Resource(StringRes.shelf_details_failed_to_delete, error.message ?: "")
                )
            },
            onSuccess = {
                // Log analytics event
                analytics.log(
                    AnalyticsEvent.DeleteShelf(
                        shelfName = shelf.name,
                        origin = AnalyticsEventOrigin.ShelfDetails
                    )
                )
                navigateBack()
            }
        )
    }

    private fun handleGameClicked(intent: ShelfDetailsIntent.GameClicked) {
        analytics.log(
            AnalyticsEvent.OpenGameDetails(
                gameTitle = intent.game.title,
                origin = AnalyticsEventOrigin.ShelfDetails
            )
        )
        openGameDetails(intent.game)
    }
}

package com.bardly.shelfs.ui.list

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.games.ui.model.GameUiModel
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.shelfs.ui.model.toUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.analytics.api.AnalyticsEventOrigin
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.shelfs.domain.model.CreateShelfDomainModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias ShelfsListComponentFactory = (
    ComponentContext,
    navigateToShelfDetails: (ShelfUiModel) -> Unit,
    navigateToGameDetails: (GameUiModel) -> Unit,
) -> DefaultShelfsListPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = ShelfsListPresenter::class)
class DefaultShelfsListPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateToShelfDetails: (ShelfUiModel) -> Unit,
    @Assisted private val navigateToGameDetails: (GameUiModel) -> Unit,
    private val shelfsRepository: ShelfsRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<ShelfsListViewState, ShelfsListIntent>(componentContext),
    ShelfsListPresenter {

    override val defaultViewState = ShelfsListViewState()

    override fun onResume() {
        fetchShelfs()
    }

    override fun handleScreenIntent(intent: ShelfsListIntent) {
        when (intent) {
            is ShelfsListIntent.ShelfClicked -> openShelfDetails(intent.shelf)
            is ShelfsListIntent.GameClicked -> openGameDetails(intent.game)
            is ShelfsListIntent.CreateShelf -> createShelf(intent.name, intent.description)
            is ShelfsListIntent.ShowCreateShelfDialog -> showCreateShelfDialog()
            is ShelfsListIntent.HideCreateShelfDialog -> hideCreateShelfDialog()
        }
    }

    private fun openGameDetails(game: GameUiModel) {
        analytics.log(
            AnalyticsEvent.OpenGameDetails(
                gameTitle = game.title,
                origin = AnalyticsEventOrigin.ShelfList,
            )
        )
        navigateToGameDetails(game)
    }

    private fun showCreateShelfDialog() {
        updateOrSetSuccess { currentState ->
            currentState.copy(
                showCreateShelfDialog = true
            )
        }
    }

    private fun hideCreateShelfDialog() {
        updateOrSetSuccess { currentState ->
            currentState.copy(
                showCreateShelfDialog = false
            )
        }
    }

    private fun openShelfDetails(shelf: ShelfUiModel) {
        analytics.log(
            AnalyticsEvent.OpenShelfDetails(
                id = shelf.id,
                origin = AnalyticsEventOrigin.ShelfList,
            )
        )
        navigateToShelfDetails(shelf)
    }

    private fun createShelf(name: String, description: String?) {
        updateOrSetSuccess { currentState ->
            currentState.copy(
                isCreatingShelf = true,
                createShelfError = null
            )
        }

        launchDataOperation(
            block = {
                shelfsRepository.createShelf(
                    CreateShelfDomainModel(
                        name = name,
                        description = description
                    )
                )
            },
            onError = { error ->
                updateOrSetSuccess { currentState ->
                    currentState.copy(
                        isCreatingShelf = false,
                        showCreateShelfDialog = true,
                        createShelfError = error.toStringRes()
                    )
                }
            }
        ) { result ->
            updateOrSetSuccess { currentState ->
                currentState.copy(
                    isCreatingShelf = false,
                    showCreateShelfDialog = false
                )
            }
            fetchShelfs()
        }
    }

    private fun fetchShelfs() {
        launchOperation(
            block = {
                shelfsRepository
                    .getShelfs()
            }
        ) { result ->
            updateOrSetSuccess { currentState ->
                currentState.copy(
                    shelfs = result.toUiModel()
                )
            }
        }
    }
}

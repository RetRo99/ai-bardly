package com.bardly.shelfs.ui.list

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.shelfs.ui.model.toUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.analytics.api.AnalyticsEventOrigin
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.shelfs.domain.ShelfsRepository
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias ShelfsListComponentFactory = (
    ComponentContext,
    navigateToShelfDetails: (ShelfUiModel) -> Unit,
) -> DefaultShelfsListComponent

@Inject
@ContributesBinding(ActivityScope::class, boundType = ShelfsListComponent::class)
class DefaultShelfsListComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateToShelfDetails: (ShelfUiModel) -> Unit,
    private val shelfsRepository: ShelfsRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<ShelfsListViewState, ShelfsListIntent>(componentContext),
    ShelfsListComponent {

    override val defaultViewState = ShelfsListViewState()

    init {
        fetchShelfs()
    }

    override fun handleScreenIntent(intent: ShelfsListIntent) {
        when (intent) {
            is ShelfsListIntent.ShelfClicked -> openShelfDetails(intent.shelf)
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

    private fun fetchShelfs(
    ) {
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

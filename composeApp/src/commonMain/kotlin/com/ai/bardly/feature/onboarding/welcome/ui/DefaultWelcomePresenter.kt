package com.ai.bardly.feature.onboarding.welcome.ui

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias WelcomePresenterFactory = (
    ComponentContext,
    openMain: () -> Unit,
) -> DefaultWelcomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = WelcomePresenter::class)
class DefaultWelcomePresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openMain: () -> Unit,
) : BasePresenterImpl<WelcomeViewState, WelcomeIntent>(componentContext), WelcomePresenter {

    override val defaultViewState = WelcomeViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun handleScreenIntent(intent: WelcomeIntent) {
        when (intent) {
            WelcomeIntent.OpenMain -> openMain()
        }
    }

}
package com.ai.bardly.feature.login.ui

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.arkivanov.decompose.ComponentContext
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias SignInPresenterFactory = (
    ComponentContext,
) -> DefaultSignInPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = SignInPresenter::class)
class DefaultSignInPresenter(
    @Assisted componentContext: ComponentContext,
    private val analytics: Analytics,
) : BasePresenterImpl<SignInViewState, SignInIntent>(componentContext), SignInPresenter {

    override val defaultViewState = SignInViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: SignInIntent) {

    }
}
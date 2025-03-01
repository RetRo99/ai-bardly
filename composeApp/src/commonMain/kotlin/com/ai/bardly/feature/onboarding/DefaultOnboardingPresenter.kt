package com.ai.bardly.feature.onboarding

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.onboarding.welcome.ui.WelcomePresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.retro99.base.ui.decompose.switchTab
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias OnboardingPresenterFactory = (
    ComponentContext,
    openMain: () -> Unit,
) -> DefaultOnboardingPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = OnboardingPresenter::class)
class DefaultOnboardingPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openMain: () -> Unit,
    private val welcomePresenterFactory: WelcomePresenterFactory,
) : OnboardingPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<OnboardingPresenter.OnboardingConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = OnboardingPresenter.OnboardingConfig.serializer(),
        initialConfiguration = OnboardingPresenter.OnboardingConfig.Welcome,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: OnboardingPresenter.OnboardingConfig) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: OnboardingPresenter.OnboardingConfig,
        componentContext: ComponentContext
    ): OnboardingPresenter.OnboardingChild = when (screenConfig) {
        OnboardingPresenter.OnboardingConfig.Welcome -> OnboardingPresenter.OnboardingChild.Welcome(
            welcomePresenterFactory(
                componentContext,
                openMain
            )
        )
    }
}
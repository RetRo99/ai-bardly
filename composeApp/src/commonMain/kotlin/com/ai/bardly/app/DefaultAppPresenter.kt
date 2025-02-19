package com.ai.bardly.app

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.auth.AuthPresenterFactory
import com.ai.bardly.feature.main.MainPresenterFactory
import com.ai.bardly.feature.onboarding.OnboardingPresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, boundType = AppPresenter::class)
class DefaultAppPresenter(
    componentContext: ComponentContext,
    private val mainPresenterFactory: MainPresenterFactory,
    private val onboardingPresenterFactory: OnboardingPresenterFactory,
    private val authPresenterFactory: AuthPresenterFactory,
) : AppPresenter, ComponentContext by componentContext {

    private val navigation = StackNavigation<AppPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = AppPresenter.Config.serializer(),
        initialConfiguration = AppPresenter.Config.Auth,
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun openMain() {
        navigation.pushNew(AppPresenter.Config.Main)
    }

    private fun childFactory(
        screenConfig: AppPresenter.Config,
        componentContext: ComponentContext
    ): AppPresenter.Child = when (screenConfig) {
        AppPresenter.Config.Main -> AppPresenter.Child.Main(
            mainPresenterFactory(
                componentContext,
            )
        )

        AppPresenter.Config.Onboarding -> AppPresenter.Child.Onboarding(
            onboardingPresenterFactory(
                componentContext,
                ::openMain,
            )
        )

        AppPresenter.Config.Auth -> AppPresenter.Child.Auth(
            authPresenterFactory(
                componentContext,
                ::openMain,
            )
        )
    }
}
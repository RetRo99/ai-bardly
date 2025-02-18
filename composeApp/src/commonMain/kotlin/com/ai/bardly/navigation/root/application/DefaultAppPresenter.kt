package com.ai.bardly.navigation.root.application

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.login.LoginPresenterFactory
import com.ai.bardly.feature.main.MainPresenterFactory
import com.ai.bardly.feature.onboarding.OnboardingPresenterFactory
import com.ai.bardly.navigation.root.application.AppPresenter.Child.Login
import com.ai.bardly.navigation.root.application.AppPresenter.Child.Main
import com.ai.bardly.navigation.root.application.AppPresenter.Child.Onboarding
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
    private val loginPresenterFactory: LoginPresenterFactory,
) : AppPresenter, ComponentContext by componentContext {

    private val navigation = StackNavigation<AppPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = AppPresenter.Config.serializer(),
        initialConfiguration = AppPresenter.Config.Main,
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
        AppPresenter.Config.Main -> Main(
            mainPresenterFactory(
                componentContext,
            )
        )

        AppPresenter.Config.Onboarding -> Onboarding(
            onboardingPresenterFactory(
                componentContext,
                ::openMain,
            )
        )

        AppPresenter.Config.Login -> Login(
            loginPresenterFactory(
                componentContext,
                ::openMain,
            )
        )
    }
}

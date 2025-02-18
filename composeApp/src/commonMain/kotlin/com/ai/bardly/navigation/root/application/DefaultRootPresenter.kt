package com.ai.bardly.navigation.root.application

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.login.LoginPresenterFactory
import com.ai.bardly.feature.main.MainPresenterFactory
import com.ai.bardly.feature.onboarding.OnboardingPresenterFactory
import com.ai.bardly.navigation.root.application.RootPresenter.Child.Login
import com.ai.bardly.navigation.root.application.RootPresenter.Child.Main
import com.ai.bardly.navigation.root.application.RootPresenter.Child.Onboarding
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
@ContributesBinding(ActivityScope::class, boundType = RootPresenter::class)
class DefaultRootPresenter(
    componentContext: ComponentContext,
    private val mainPresenterFactory: MainPresenterFactory,
    private val onboardingPresenterFactory: OnboardingPresenterFactory,
    private val loginPresenterFactory: LoginPresenterFactory,
) : RootPresenter, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootPresenter.Config.serializer(),
        initialConfiguration = RootPresenter.Config.Main,
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun openMain() {
        navigation.pushNew(RootPresenter.Config.Main)
    }

    private fun childFactory(
        screenConfig: RootPresenter.Config,
        componentContext: ComponentContext
    ): RootPresenter.Child = when (screenConfig) {
        RootPresenter.Config.Main -> Main(
            mainPresenterFactory(
                componentContext,
            )
        )

        RootPresenter.Config.Onboarding -> Onboarding(
            onboardingPresenterFactory(
                componentContext,
                ::openMain,
            )
        )

        RootPresenter.Config.Login -> Login(
            loginPresenterFactory(
                componentContext,
                ::openMain,
            )
        )
    }
}

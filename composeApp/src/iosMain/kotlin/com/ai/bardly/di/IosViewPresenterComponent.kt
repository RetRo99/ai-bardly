package com.ai.bardly.di

import com.ai.bardly.HomeViewController
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.navigation.root.application.RootPresenter
import com.arkivanov.decompose.ComponentContext
import platform.UIKit.UIViewController
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface IosViewPresenterComponent {
    val rootPresenter: RootPresenter

    val uiViewController: HomeViewController

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun createComponent(
            componentContext: ComponentContext,
            uiViewControllerProvider: () -> UIViewController,
        ): IosViewPresenterComponent
    }
}

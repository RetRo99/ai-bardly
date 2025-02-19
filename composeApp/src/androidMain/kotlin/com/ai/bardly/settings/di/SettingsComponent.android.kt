//package com.ai.bardly.settings.di
//
//import android.content.Context
//import com.ai.bardly.settings.EncryptedFactory
//import com.ai.bardly.settings.EncryptedPreferenceFactory
//import me.tatarka.inject.annotations.Provides
//import software.amazon.lastmile.kotlin.inject.anvil.AppScope
//import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
//import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
//
//@ContributesTo(AppScope::class)
//@SingleIn(AppScope::class)
//interface AndroidSettingsComponent {
//
//    @Provides
//    @SingleIn(AppScope::class)
//    fun provideEncryptedFactory(
//        context: Context
//    ): EncryptedFactory = EncryptedPreferenceFactory(context)
//}
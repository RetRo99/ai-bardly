package com.ai.bardly.di

import android.app.Application
import android.content.Context
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.database.AppDatabase
import com.ai.bardly.database.PlatformDataBaseHelper
import com.ai.bardly.networking.getHttpEngine
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics
import dev.gitlive.firebase.crashlytics.crashlytics
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import javax.inject.Named

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppComponent(
    @get:Provides val application: Application,
) : ActivityComponent.Factory {
    abstract val activityComponentFactory: ActivityComponent.Factory

    @Provides
    @SingleIn(AppScope::class)
    fun provideContext(application: Application): Context =
        application

    // region Database TODO: Move to a separate module
    @Provides
    @SingleIn(AppScope::class)
    fun provideAppDatabase(dataBaseHelper: PlatformDataBaseHelper): AppDatabase =
        dataBaseHelper.getDatabaseBuilder().build()

    @Provides
    @SingleIn(AppScope::class)
    fun provideGamesDao(appDatabase: AppDatabase) = appDatabase.getGamesDao()

    @Provides
    @SingleIn(AppScope::class)
    fun provideMessagesDao(appDatabase: AppDatabase) = appDatabase.getMessagesDao()
    // endregion

    // region BuildConfig TODO: Move to a separate module
    @Provides
    @SingleIn(AppScope::class)
    fun provideBuildConfig(): BuildConfig = getBuildConfig()

    @Provides
    @SingleIn(AppScope::class)
    @Named("isDebug")
    fun provideIsDebug(buildConfig: BuildConfig): Boolean = buildConfig.isDebug
    // endregion

    // region Analytics TODO: Move to a separate module
    @Provides
    @SingleIn(AppScope::class)
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    @SingleIn(AppScope::class)
    fun provideCrashlytics(): FirebaseCrashlytics = Firebase.crashlytics

    @Provides
    @SingleIn(AppScope::class)
    fun provideAnalytics(
        firebaseAnalytics: FirebaseAnalytics,
        firebaseCrashlytics: FirebaseCrashlytics,
        @Named("isDebug") isDebug: Boolean,
    ): Analytics = if (isDebug) DebugAnalyticsManager() else AnalyticsManager(
        firebaseAnalytics,
        firebaseCrashlytics
    )

    //endregion

    // region Networking TODO: Move to a separate module
    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = getHttpEngine()

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(
        httpFactory: HttpClientEngineFactory<*>,
        analytics: Analytics
    ): HttpClient {
        val json = Json { ignoreUnknownKeys = true }
        return HttpClient(httpFactory) {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, request ->
                    analytics.logException(cause, request.url.toString())
                }
            }
        }
    }

    //endregion
    companion object
}

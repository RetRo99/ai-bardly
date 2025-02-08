package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.database.AppDatabase
import com.ai.bardly.database.PlatformDataBaseHelper
import com.ai.bardly.networking.getHttpEngine
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
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
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

@Module
class DatabaseModule {
    @Single
    fun provideAppDatabase(dataBaseHelper: PlatformDataBaseHelper): AppDatabase =
        dataBaseHelper.getDatabaseBuilder().build()

    @Single
    fun provideGamesDao(appDatabase: AppDatabase) = appDatabase.getGamesDao()

    @Single
    fun provideMessagesDao(appDatabase: AppDatabase) = appDatabase.getMessagesDao()
}

@Module
class BuildConfigModule {
    @Single
    fun provideBuildConfig(): BuildConfig = getBuildConfig()
}

@Module
class NetworkingModule {
    @Single
    fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = getHttpEngine()

    @Single
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
}

@Module
class AnalyticsModule {
    @Single
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Single
    fun provideCrashlytics(): FirebaseCrashlytics = Firebase.crashlytics

    @Single
    @Named("isDebug")
    fun provideIsDebug(buildConfig: BuildConfig): Boolean = buildConfig.isDebug

    @Single
    fun provideAnalytics(
        firebaseAnalytics: FirebaseAnalytics,
        firebaseCrashlytics: FirebaseCrashlytics,
        @Named("isDebug") isDebug: Boolean,
    ): Analytics = if (isDebug) DebugAnalyticsManager() else AnalyticsManager(
        firebaseAnalytics,
        firebaseCrashlytics
    )
}

@Module(includes = [DatabaseModule::class, NetworkingModule::class, AnalyticsModule::class, BuildConfigModule::class])
@ComponentScan("com.ai.bardly")
class ApplicationModule

fun initKoin(
    appModule: org.koin.core.module.Module,
    appDeclaration: KoinAppDeclaration? = null
) {
    // TODO move somewhere else
    GoogleAuthProvider.create(GoogleAuthCredentials(serverId = "202431209061-0ku3miec01ehdhkp84jcpmp6lfbpefeq.apps.googleusercontent.com"))

    startKoin {
        if (appDeclaration != null) {
            appDeclaration()
        }
        modules(appModule)
    }
}

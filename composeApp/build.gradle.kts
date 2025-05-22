import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.essenty.backhandler)
            export(libs.essenty.stateKeeper)
        }
    }
    jvmToolchain(libs.versions.jdk.get().toInt())

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            // work around for https://github.com/GitLiveApp/firebase-kotlin-sdk/issues/676
            implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
            implementation("com.google.firebase:firebase-analytics-ktx:22.3.0")
            implementation("com.google.firebase:firebase-crashlytics-ktx:19.4.1")
        }
        iosMain.dependencies {
        }
        commonMain.dependencies {
            api(libs.bundles.decompose)
            api(libs.essenty.stateKeeper)
            api(libs.essenty.backhandler)
            api(libs.essenty.lifecycle)
            implementation(libs.essenty.coroutines)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.datetime)
            implementation(libs.paging.common)
            implementation(libs.bundles.kotlinInject)
            implementation(libs.markdown)
            implementation(projects.lib.network.api)
            implementation(projects.lib.network.implementation)
            implementation(projects.lib.preferences.api)
            implementation(projects.lib.preferences.implementation)
            implementation(projects.lib.paging.domain)
            implementation(projects.lib.paging.ui)
            implementation(projects.lib.database.implementation)
            implementation(projects.lib.database.api)
            implementation(projects.baseUi)
            implementation(projects.base)
            implementation(projects.feature.games.ui)
            implementation(projects.feature.games.domain)
            implementation(projects.feature.games.data)
            implementation(projects.feature.shelfs.ui)
            implementation(projects.feature.shelfs.domain)
            implementation(projects.feature.shelfs.data)
            implementation(projects.translations)
            implementation(projects.lib.analytics.api)
            implementation(projects.lib.analytics.implementation)
            implementation(projects.feature.chats.data)
            implementation(projects.feature.chats.domain)
            implementation(projects.feature.chats.ui)
            implementation(projects.feature.home.ui)
            implementation(projects.feature.auth.data)
            implementation(projects.feature.auth.domain)
            implementation(projects.feature.auth.ui)
            implementation(projects.feature.user.data)
            implementation(projects.feature.user.domain)
            implementation(projects.feature.user.ui)
            implementation(projects.feature.main)
            implementation(projects.lib.snackbar.api)
            implementation(projects.lib.snackbar.implementation)
        }
    }
}

android {
    namespace = "com.ai.bardly"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ai.bardly"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 22
        versionName = "0.0.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    signingConfigs {
        // Add the release signing config
        create("release") {
            // Try to load keystore.properties file
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))

                storeFile = file(keystoreProperties["storeFile"].toString())
                storePassword = keystoreProperties["storePassword"].toString()
                keyAlias = keystoreProperties["keyAlias"].toString()
                keyPassword = keystoreProperties["keyPassword"].toString()
            }
        }
    }
    buildTypes {
        getByName("debug") {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
            manifestPlaceholders["usesCleartextTraffic"] = true
            resValue("string", "app_name", "Bardy Debug")
        }
        getByName("release") {
            manifestPlaceholders += mapOf(
                "crashlyticsCollectionEnabled" to true,
                "usesCleartextTraffic" to false
            )
            isMinifyEnabled = true
            isMinifyEnabled = false
            resValue("string", "app_name", "Bardy")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    debugImplementation(libs.androidx.compose.ui.tooling)
    add("kspAndroid", libs.kotlinInject.compiler)
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

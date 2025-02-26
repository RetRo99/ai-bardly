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
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
        freeCompilerArgs.add("-Xwhen-guards")
    }
    jvmToolchain(libs.versions.jdk.get().toInt())

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
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
        versionCode = 5
        versionName = "0.0.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
            manifestPlaceholders["usesCleartextTraffic"] = true
            resValue("string", "app_name", "Bardly Debug")
        }
        getByName("release") {
            manifestPlaceholders += mapOf()
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            manifestPlaceholders["usesCleartextTraffic"] = false
            isMinifyEnabled = true // Enable minification here
            isMinifyEnabled = false
            resValue("string", "app_name", "Bardly")
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
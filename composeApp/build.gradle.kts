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
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.security.crypto)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            api(libs.essenty.lifecycle)
            api(libs.essenty.stateKeeper)
            api(libs.essenty.backhandler)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.sqlite.bundled)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kermit)
            implementation(libs.androidx.room.runtime)
            implementation(libs.datetime)
            implementation(libs.kmpauth.google)
            implementation(libs.kmpauth.firebase)
            implementation(libs.kmpauth.uihelper)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.androidx.room.paging)
            implementation(libs.paging.common)
            api(libs.gitlive.firebase.kotlin.crashlytics)
            api(libs.gitlive.firebase.kotlin.analytics)
            api(libs.bundles.decompose)
            implementation(libs.essenty.coroutines)
            implementation(libs.bundles.kotlinInject)
            implementation(libs.markdown)
            implementation(libs.multiplatformSettings)
        }
//        configurations.all {
//            resolutionStrategy.eachDependency {
//                if (requested.group == "androidx.paging" && requested.name == "paging-compose") {
//                    useVersion("3.3.5")
//                    because("Enforce specific paging-compose version")
//                }
//            }
//        }
    }
}

android {
    namespace = "com.ai.bardly"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }
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
    ksp(libs.androidx.room.compiler)
    add("kspAndroid", libs.kotlinInject.compiler)
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}
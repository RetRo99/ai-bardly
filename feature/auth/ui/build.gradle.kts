plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

version = "1.0"

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xwhen-guards")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.base)
            implementation(projects.baseUi)
            implementation(projects.lib.analytics.api)
            implementation(projects.feature.auth.domain)
            implementation(projects.feature.user.domain)
            implementation(projects.feature.user.ui)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.bundles.kotlinInject)
            implementation(libs.gitlive.firebase.kotlin.auth)
            implementation(libs.kmpauth.google)
            implementation(libs.kmpauth.uihelper)
            implementation(libs.kmpauth.firebase)
        }

        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

android {
    namespace = "com.retro99.auth.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

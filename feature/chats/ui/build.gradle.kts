plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.ksp)
}

version = "1.0"

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.chats.domain)
            implementation(projects.baseUi)
            implementation(projects.base)
            implementation(projects.lib.analytics.api)
            implementation(projects.feature.games.ui)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.markdown)
            implementation(libs.bundles.kotlinInject)
            implementation(libs.datetime)

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
    namespace = "com.retro99.chats.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

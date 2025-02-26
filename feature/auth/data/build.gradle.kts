plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
            implementation(projects.base)
            implementation(projects.feature.auth.domain)
            implementation(projects.feature.user.data)

            implementation(projects.lib.network.api)
            implementation(projects.lib.preferences.api)
            implementation(projects.lib.preferences.api)
            implementation(libs.bundles.kotlinInject)
            api(libs.gitlive.firebase.kotlin.auth)
            api(libs.kmpauth.google)
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

android {
    namespace = "com.retro99.auth.data"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

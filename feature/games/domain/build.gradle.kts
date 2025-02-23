plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
            implementation(projects.lib.paging.domain)
        }
    }
}

android {
    namespace = "com.retro99.games.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

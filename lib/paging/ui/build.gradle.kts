plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
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
            implementation(libs.paging.common)
            implementation(projects.lib.paging.domain)
            implementation(compose.runtime)
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "com.retro99.paging.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

plugins {
    alias(libs.plugins.kotlinxSerialization)
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

        }

        androidMain.dependencies {

        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "com.retro99.preferences.api"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

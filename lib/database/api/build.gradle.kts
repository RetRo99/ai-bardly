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
            implementation(libs.room.runtime)
            implementation(projects.lib.paging.domain)
            implementation(libs.datetime)
        }
    }
}

android {
    namespace = "com.bardly.database.api"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

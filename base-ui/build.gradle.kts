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
            implementation(projects.base)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            api(compose.components.resources)
            api(libs.bundles.decompose)
            implementation(libs.essenty.lifecycle)
            implementation(libs.essenty.coroutines)
            implementation(libs.datetime)
            api(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            api(projects.translations)
        }
    }
}
compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "resources.icons"
}

android {
    namespace = "com.retro99.base.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

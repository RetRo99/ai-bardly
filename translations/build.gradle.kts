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
            api(compose.components.resources)
            implementation(compose.runtime)
        }
    }

}
compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "resources.translations"
}
android {
    namespace = "com.retro99.translations"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

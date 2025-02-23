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
            implementation(projects.feature.chats.domain)
            implementation(libs.bundles.kotlinInject)
            implementation(projects.lib.database.api)
            implementation(libs.datetime)
            implementation(libs.room.runtime)
            implementation(projects.lib.database.api)
            implementation(projects.base)
            implementation(projects.lib.network.api)
            implementation(libs.serialization)
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

android {
    namespace = "com.retro99.chats.data"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

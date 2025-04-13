plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinxSerialization)
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
            implementation(projects.feature.shelfs.domain)
            implementation(projects.lib.paging.domain)
            implementation(libs.bundles.kotlinInject)
            implementation(libs.serialization)
            implementation(projects.lib.network.api)
            implementation(projects.base)
            implementation(projects.lib.database.api)
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

android {
    namespace = "com.retro99.shelfs.data"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

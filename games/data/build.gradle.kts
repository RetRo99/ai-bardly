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
            implementation(projects.games.domain)
            implementation(projects.paging.domain)
            implementation(libs.bundles.kotlinInject)
            implementation(libs.androidx.room.runtime)
            implementation(libs.datetime)
            implementation(projects.network.api)
            implementation(projects.base)
            implementation(projects.database.api)
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

android {
    namespace = "com.retro99.games.data"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

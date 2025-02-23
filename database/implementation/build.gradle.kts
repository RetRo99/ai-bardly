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
            implementation(libs.bundles.kotlinInject)
            implementation(libs.datetime)
            implementation(libs.sqlite.bundled)
            implementation(projects.database.api)
            implementation(projects.base)
            implementation(projects.games.data)
            implementation(projects.paging.domain)
            api(libs.androidx.room.runtime)
            implementation(libs.androidx.room.paging)
            implementation(projects.analytics.api)
        }
    }
}

android {
    namespace = "com.bardly.database.implementation"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

dependencies {
    ksp(libs.androidx.room.compiler)
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}

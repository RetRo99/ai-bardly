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
            implementation(projects.lib.database.api)
            implementation(projects.base)
            implementation(projects.lib.paging.domain)
            api(libs.room.runtime)
            implementation(libs.androidx.room.paging)
            implementation(projects.lib.analytics.api)
        }
    }
}

android {
    namespace = "com.bardly.database.implementation"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

dependencies {
    // Room compiler for all platforms
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)

    // KotlinInject Anvil compiler (already correct)
    add("kspAndroid", libs.kotlinInject.anvil.compiler)
    add("kspIosArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlinInject.anvil.compiler)
    add("kspIosX64", libs.kotlinInject.anvil.compiler) // Added this for consistency
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}

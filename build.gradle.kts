plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.androidLibrary) apply false
}

// Use a different approach for configuring KMP modules
subprojects {
    // This will run after each subproject is evaluated
    afterEvaluate {
        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            // Access the kotlin extension after the KMP plugin is applied
            extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()
                ?.apply {
                    targets.configureEach {
                        compilations.configureEach {
                            compileTaskProvider.get().compilerOptions {
                                freeCompilerArgs.add("-Xexpect-actual-classes")
                                freeCompilerArgs.add("-Xwhen-guards")
                            }
                        }
                    }
                }
        }
    }
}
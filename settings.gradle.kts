rootProject.name = "ai-bardly"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":network:implementation")
include(":network:api")
include(":preferences:api")
include(":preferences:implementation")
include(":games:data")
include(":games:domain")
include(":games:ui")
include(":paging:domain")
include(":paging:ui")
include(":database:implementation")
include(":database:api")
include(":base-ui")
include(":base")
include(":translations")
include(":analytics:api")
include(":analytics:implementation")
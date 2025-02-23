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
include(":feature:games:data")
include(":feature:games:domain")
include(":feature:games:ui")
include(":paging:domain")
include(":paging:ui")
include(":database:implementation")
include(":database:api")
include(":base-ui")
include(":base")
include(":translations")
include(":analytics:api")
include(":analytics:implementation")
include(":feature:chats:data")
include(":feature:chats:domain")
include(":feature:chats:ui")
include(":feature:home:ui")
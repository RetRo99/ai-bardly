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
include(":lib:network:implementation")
include(":lib:network:api")
include(":lib:preferences:api")
include(":lib:preferences:implementation")
include(":feature:games:data")
include(":feature:games:domain")
include(":feature:games:ui")
include(":lib:paging:domain")
include(":lib:paging:ui")
include(":lib:database:implementation")
include(":lib:database:api")
include(":base-ui")
include(":base")
include(":translations")
include(":lib:analytics:api")
include(":lib:analytics:implementation")
include(":feature:chats:data")
include(":feature:chats:domain")
include(":feature:chats:ui")
include(":feature:home:ui")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:ui")
include(":feature:user:data")
include(":feature:user:domain")
include(":feature:user:ui")
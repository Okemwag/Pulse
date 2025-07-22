pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pulse"
include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:blockchain")
include(":core:design")
include(":feature:auth")
include(":feature:news")
include(":feature:alerts")
include(":feature:classifieds")
include(":feature:profile")
include(":feature:wallet")
include(":shared:domain")
include(":shared:data")
include(":shared:ui")

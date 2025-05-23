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

rootProject.name = "Games"
include(":app")
include(":feature-bulls-and-cows-ui")
include(":feature-bulls-and-cows-data")
include(":feature-tic-tac-toe-ui")
include(":feature-tic-tac-toe-data")
include(":feature-fifteen-ui")
include(":feature-fifteen-data")
include(":feature-reversi-ui")
include(":feature-reversi-data")
include(":shared")

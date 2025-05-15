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

rootProject.name = "SuperformulaChallenge"
include(":app")
include(":core")
include(":core-ui")
include(":common")
include(":feature")
include(":feature:qr-generator")
include(":feature:qr-scanner")
include(":feature:qr-scanner:presentation")
include(":feature:qr-scanner:data")
include(":feature:qr-scanner:domain")
include(":feature:qr-generator:presentation")
include(":feature:qr-generator:data")
include(":feature:qr-generator:domain")

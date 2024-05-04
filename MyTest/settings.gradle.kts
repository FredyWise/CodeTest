pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MyTest"
include(":app")
include(":textrecognition")
include(":chatgpt")
include(":testproject")
include(":ocr")
include(":mysavingsui")
include(":mysavingsscreens")
include(":roomdatabase")
include(":roomdatabase3")
include(":otp")
include(":testingbiometric")

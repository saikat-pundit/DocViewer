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
        // Added JitPack to resolve com.github.barteksc:android-pdf-viewer
        maven { url = uri("https://jitpack.io") } 
    }
}

rootProject.name = "FileViewer"
include(":app")

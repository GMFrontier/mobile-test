// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {/*
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false*/
    id(Plugins.androidLibraryPlugin) version (Plugins.agp) apply false
    id(Plugins.kotlinAndroidPlugin) version (Kotlin.version) apply false
    id(Plugins.kotlinComposePlugin) version (Kotlin.version) apply false
    id(Plugins.hiltAndroidPlugin) version (DaggerHilt.version) apply false
    id(Plugins.kspPlugin) version (Plugins.kspVersion) apply false
    id(Plugins.kotlinSerializationPlugin) version (Kotlin.version) apply false
}
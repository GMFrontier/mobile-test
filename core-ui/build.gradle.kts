plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "core_ui"
}
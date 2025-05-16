plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
    id(Plugins.kotlinSerializationPlugin) version (Kotlin.version)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "qr_generator_domain"
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.core))

    implementation(Kotlin.kotlinSerializationJson)
}
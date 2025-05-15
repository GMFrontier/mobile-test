plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "qr_generator_domain"
}

dependencies {
    implementation(project(Modules.core))
}
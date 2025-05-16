import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
}

val props = Properties().apply {
    load(FileInputStream(rootProject.file("config.properties")))
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "core"
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${props["BASE_URL"]}\"")
        buildConfigField("String", "API_KEY", "\"${props["API_KEY"]}\"")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {


    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.gson)
}
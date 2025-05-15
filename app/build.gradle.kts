plugins {
    id(Plugins.androidApplicationPlugin)
    id(Plugins.kotlinAndroidPlugin) version (Kotlin.version)
    id(Plugins.kotlinComposePlugin) version (Kotlin.version)
    id(Plugins.kspPlugin)
    id(Plugins.hiltAndroidPlugin)
}

android {
    namespace = "com.frommetoyou.superformulachallenge"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = ProjectConfig.appIdPrefix + "HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.1.5") // Or the latest version

    implementation(project(Modules.qrGeneratorPresentation))
    implementation(project(Modules.qrScannerPresentation))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(Compose.activityCompose)
    implementation(platform(Compose.composeBom))
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material3)

    implementation(DaggerHilt.hiltAndroid)
    implementation(DaggerHilt.hiltNavigationCompose)
    ksp(DaggerHilt.hiltCompiler)
    ksp(DaggerHilt.hiltAndroidCompiler)

    testImplementation(Testing.junit)
    testImplementation(Testing.assertk)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)

    androidTestImplementation(Testing.assertk)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.junit)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.rules)
    androidTestImplementation(Testing.uiTestManifest)
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(platform(Compose.composeBom))
    androidTestImplementation(Testing.hiltTesting)
    androidTestImplementation(Testing.testRunner)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Testing.uiTestManifest)
}
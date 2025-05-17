plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "qr_scanner_presentation"
}

dependencies {

    implementation(project(Modules.coreUi))
    implementation(project(Modules.common))

    implementation(Camera.camera2)
    implementation(Camera.cameraCore)
    implementation(Camera.cameraView)
    implementation(Camera.cameraLifecycle)

    implementation(Retrofit.gson)

    implementation(QrCode.barcodeScanning)

    implementation(Permissions.accompanistPermissions)
}
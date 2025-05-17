@file:OptIn(ExperimentalPermissionsApi::class)

package com.frommetoyou.presentation

import android.Manifest.permission.CAMERA
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.frommetoyou.common.util.findAndroidActivity
import com.frommetoyou.common.util.goToAppSettings
import com.frommetoyou.core_ui.components.CameraPermissionTextProvider
import com.frommetoyou.core_ui.components.PermissionDialog
import com.frommetoyou.presentation.composables.QRCodeScannerWithBottomSheet
import com.frommetoyou.presentation.composables.QRScannerGuideWithPath
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun QRScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: QrScannerViewModel = hiltViewModel()
) {
    val activity = LocalContext.current.findAndroidActivity()

    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val permissionEvent = viewModel.permissionEvent.collectAsState().value

    val permissionsToRequest = arrayOf(CAMERA)

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    val shouldLaunchPermissionRequest = remember { mutableStateOf(true) }

    LaunchedEffect(shouldLaunchPermissionRequest.value) {
        if (shouldLaunchPermissionRequest.value) {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
            shouldLaunchPermissionRequest.value = false
        }
    }

    val cameraPermissionState =
        rememberPermissionState(permission = CAMERA) { isGranted ->
            if (isGranted)
                println("successfully granted")
            else
                println("no granted")
        }

    viewModel.setPermissionState(cameraPermissionState)

    LaunchedEffect(permissionEvent) {
        when (permissionEvent) {
            is PermissionEvent.PermissionRequest -> {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }

            else -> {}
        }
    }
    Box {
        QRCodeScannerWithBottomSheet(viewModel)
        QRScannerGuideWithPath()
    }

    dialogQueue
        .forEach { permission ->

        PermissionDialog(
            permissionTextProvider = CameraPermissionTextProvider(),
            isPermanentlyDenied = !(activity?.shouldShowRequestPermissionRationale(
                permission
            ) ?: true),
            onDismiss = viewModel::dismissDialog,
            onOkClick = {
                viewModel.dismissDialog()
                multiplePermissionResultLauncher.launch(arrayOf(permission))
            },
            onGoToAppSettingsClick = {
                activity?.goToAppSettings()
            }
        )
    }
}

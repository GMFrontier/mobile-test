package com.frommetoyou.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.common.model.QrModel
import com.frommetoyou.core_ui.utils.parseExpirationTime
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPermissionsApi::class)
@HiltViewModel
class QrScannerViewModel @Inject constructor(
) : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _permissionEvent = MutableStateFlow<PermissionEvent>(PermissionEvent.Idle)
    val permissionEvent: StateFlow<PermissionEvent> = _permissionEvent.asStateFlow()

    private val _qrScannerUiState =
        MutableStateFlow<QRScannerUiState>(QRScannerUiState.Nothing)
    val qrScannerUiState = _qrScannerUiState.asStateFlow()

    private lateinit var cameraPermissionState: PermissionState

    private var timerJob: Job? = null

    /***
     * Since i've asked for a lambda in the challenge, I used what i've been provided.
     * I didn't had access to a backend endpoint to validate the QR, and I wanted
     * to clarify that I understand that the below method is not secure since
     * its a frontend validation trusting another client's data.
     * ***/
    fun isValidQR(scannedCode: String): Boolean {
        val qrCode = Gson().fromJson(scannedCode, QrModel::class.java)
        val expirationTime = parseExpirationTime(qrCode.expiresAt)
        val timeLeftMillis = expirationTime - System.currentTimeMillis()

        timerJob?.cancel()

        if (timeLeftMillis > 0) {
            _qrScannerUiState.value = QRScannerUiState.ValidQR(qrCode)

            timerJob = viewModelScope.launch {
                delay(timeLeftMillis)
                _qrScannerUiState.value = QRScannerUiState.Expired
            }

            return true
        } else {
            _qrScannerUiState.value = QRScannerUiState.Expired
            return false
        }
    }


    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) visiblePermissionDialogQueue.add(
            permission
        )
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeAt(0)
    }

    fun setPermissionState(permissionState: PermissionState) {
        cameraPermissionState = permissionState
    }
}


sealed interface QRScannerUiState {
    data class ValidQR(val qrModel: QrModel) : QRScannerUiState
    data object Expired : QRScannerUiState
    data object Nothing : QRScannerUiState
}

sealed class PermissionEvent {
    object Idle : PermissionEvent()
    object PermissionRequest : PermissionEvent()
}

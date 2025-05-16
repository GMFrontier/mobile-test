package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.common.model.QrModel
import com.frommetoyou.core_ui.utils.parseExpirationTime
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
) : ViewModel() {

    private val _qrScannerUiState =
        MutableStateFlow<QRScannerUiState>(QRScannerUiState.Nothing)
    val qrScannerUiState = _qrScannerUiState.asStateFlow()

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

}


sealed interface QRScannerUiState {
    data class ValidQR(val qrModel: QrModel) : QRScannerUiState
    data object Expired : QRScannerUiState
    data object Nothing : QRScannerUiState
}
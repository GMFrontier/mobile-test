package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.common.util.Result
import com.frommetoyou.common.util.asResult
import com.frommetoyou.common.model.QrModel
import com.frommetoyou.core_ui.utils.parseExpirationTime
import com.frommetoyou.domain.use_case.QrCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRGeneratorViewModel @Inject constructor(
    private val qrCodeUseCase: QrCodeUseCase
) : ViewModel() {

    private val _qrGeneratorUiState =
        MutableStateFlow<QRGeneratorUiState>(QRGeneratorUiState.Nothing)
    val qrGeneratorUiState = _qrGeneratorUiState.asStateFlow()

    private val _timeLeftMillis = MutableStateFlow(0L)
    val timeLeftMillis = _timeLeftMillis.asStateFlow()

    fun getQrCode() = viewModelScope.launch {
        qrCodeUseCase.getQrCode()
            .asResult()
            .collect { result ->
                _qrGeneratorUiState.value = when (result) {
                    is Result.Success -> {
                        startCountdown(result.data.expiresAt)
                        QRGeneratorUiState.Success(result.data)
                    }
                    is Result.Error -> QRGeneratorUiState.Error
                    is Result.Loading -> QRGeneratorUiState.Loading
                }
            }
    }

    private fun startCountdown(expiresAt: String) {
        val expirationTime = parseExpirationTime(expiresAt)

        viewModelScope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis()
                val remaining = expirationTime - currentTime
                if (remaining > 0) {
                    _timeLeftMillis.value = remaining
                } else {
                    _timeLeftMillis.value = 0
                    break
                }
                delay(1000)
            }
        }
    }
}

sealed interface QRGeneratorUiState {
    data class Success(val qrCode: QrModel) : QRGeneratorUiState
    data object Error : QRGeneratorUiState
    data object Loading : QRGeneratorUiState
    data object Nothing : QRGeneratorUiState
}
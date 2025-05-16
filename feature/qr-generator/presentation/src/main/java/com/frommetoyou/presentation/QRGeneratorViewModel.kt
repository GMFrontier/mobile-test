package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.common.util.Result
import com.frommetoyou.common.util.asResult
import com.frommetoyou.domain.model.QrModel
import com.frommetoyou.domain.use_case.QrCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getQrCode() = viewModelScope.launch {
        qrCodeUseCase.getQrCode()
            .asResult()
            .collect { result ->
                _qrGeneratorUiState.value = when (result) {
                    is Result.Success -> QRGeneratorUiState.Success(result.data)
                    is Result.Error -> QRGeneratorUiState.Error
                    is Result.Loading -> QRGeneratorUiState.Loading
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
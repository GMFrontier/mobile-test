package com.frommetoyou.domain.use_case

import com.frommetoyou.domain.repository.QrRepository
import javax.inject.Inject

class QrCodeUseCase @Inject constructor(
    private val qrRepository: QrRepository
) {
    suspend fun getQrCode() = qrRepository.getQrCode()

}
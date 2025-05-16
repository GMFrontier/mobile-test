package com.frommetoyou.domain.repository

import com.frommetoyou.domain.model.QrModel
import kotlinx.coroutines.flow.Flow

interface QrRepository {
    suspend fun getQrCode(): Flow<QrModel>
}
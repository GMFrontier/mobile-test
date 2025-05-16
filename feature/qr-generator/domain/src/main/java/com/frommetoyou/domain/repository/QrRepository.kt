package com.frommetoyou.domain.repository

import com.frommetoyou.common.model.QrModel
import kotlinx.coroutines.flow.Flow

interface QrRepository {
    suspend fun getQrCode(): Flow<QrModel>
}
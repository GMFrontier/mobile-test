package com.frommetoyou.domain.repository

import com.frommetoyou.common.model.QrModel
import com.frommetoyou.common.util.Result
import kotlinx.coroutines.flow.Flow

interface QrRepository {
    suspend fun getQrCode(): Flow<Result<QrModel>>
}
package com.frommetoyou.data.repository

import javax.inject.Inject
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.core.util.parseResponse
import com.frommetoyou.data.service.QrApiService
import com.frommetoyou.domain.model.QrModel
import com.frommetoyou.domain.repository.QrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class QrCodeRepositoryImpl @Inject constructor(
    private val qrApiService: QrApiService,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : QrRepository {
    override suspend fun getQrCode(): Flow<QrModel> = flow {
        emit(qrApiService.getQrCode("VVTUTAdalB55yRKQzsM7u6RTowrcUUhJLA82hoN6"))
    }
        .map { result ->
            val qrCode = result.body() as QrModel
            qrCode
        }
        .flowOn(coroutinesDispatcherProvider.io)
}

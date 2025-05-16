package com.frommetoyou.data.repository

import com.frommetoyou.common.model.QrModel
import com.frommetoyou.common.util.CoroutinesDispatcherProvider
import com.frommetoyou.common.util.Result
import com.frommetoyou.common.util.asResult
import com.frommetoyou.data.service.QrApiService
import com.frommetoyou.domain.repository.QrRepository
import com.frommetoyou.superformulachallenge.core.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QrCodeRepositoryImpl @Inject constructor(
    private val qrApiService: QrApiService,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : QrRepository {
    override suspend fun getQrCode(): Flow<Result<QrModel>> = flow {
        emit(qrApiService.getQrCode(BuildConfig.API_KEY))
    }
        .map { result ->
            val qrCode = result.body() as QrModel
            qrCode
        }
        .asResult()
        .flowOn(coroutinesDispatcherProvider.io)
}

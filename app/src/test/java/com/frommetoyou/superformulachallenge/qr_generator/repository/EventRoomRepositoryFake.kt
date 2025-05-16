package com.frommetoyou.superformulachallenge.qr_generator.repository

import com.frommetoyou.common.model.QrModel
import com.frommetoyou.domain.repository.QrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QrRepositoryFake : QrRepository {

    var qrModel: QrModel = QrModel.getDefaultModel()

    override suspend fun getQrCode(): Flow<QrModel> = flow {
        emit(qrModel)
    }
}
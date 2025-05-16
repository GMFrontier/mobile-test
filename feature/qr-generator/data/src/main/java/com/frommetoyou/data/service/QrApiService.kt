package com.frommetoyou.data.service

import com.frommetoyou.common.model.QrModel
import com.frommetoyou.superformulachallenge.core.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface QrApiService {

    @GET("random-qr-seed_seed")
    suspend fun getQrCode(
        @Header("x-api-key") apiKey: String = BuildConfig.API_KEY
    ) : Response<QrModel>
}
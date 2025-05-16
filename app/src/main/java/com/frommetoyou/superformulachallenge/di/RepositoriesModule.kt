package com.frommetoyou.superformulachallenge.di

import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.data.repository.QrCodeRepositoryImpl
import com.frommetoyou.data.service.QrApiService
import com.frommetoyou.domain.repository.QrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideQrRepository(
        qrApiService: QrApiService,
        coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    ): QrRepository {
        return QrCodeRepositoryImpl(qrApiService, coroutinesDispatcherProvider)
    }
}
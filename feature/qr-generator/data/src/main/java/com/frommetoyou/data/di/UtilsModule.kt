package com.frommetoyou.data.di

import com.frommetoyou.common.util.CoroutinesDispatcherProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider()
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}
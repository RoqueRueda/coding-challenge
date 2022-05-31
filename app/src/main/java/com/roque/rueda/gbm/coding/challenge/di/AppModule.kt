package com.roque.rueda.gbm.coding.challenge.di

import com.google.gson.Gson
import com.roque.rueda.gbm.coding.challenge.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesMockyBaseUrl(): String =
        BuildConfig.BASE_URL

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
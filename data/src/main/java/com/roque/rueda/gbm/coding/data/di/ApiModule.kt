package com.roque.rueda.gbm.coding.data.di

import com.roque.rueda.gbm.coding.data.ipc.MockyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideMockyApi(retrofit: Retrofit): MockyApi =
        retrofit.create(MockyApi::class.java)

}
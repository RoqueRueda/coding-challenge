package com.roque.rueda.gbm.coding.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideJsonParser(): Json =
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
            allowSpecialFloatingPointValues = true
            coerceInputValues = true
            prettyPrint = true
        }

    @Provides
    @ExperimentalSerializationApi
    fun provideJsonConverterFactory(
        jsonParser: Json
    ): Converter.Factory = jsonParser.asConverterFactory("application/json".toMediaType())

    @Provides
    fun provideBaseOkHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient =
        okHttpClientBuilder.build()

    @Provides
    @Singleton
    fun provideMockyRetrofit (
        baseUrl: String,
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverterFactory)
            .build()
}
package com.voxeldev.tinkofflab.di.modules

import com.voxeldev.tinkofflab.BuildConfig
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.DaDataApi
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.interceptors.ApiKeyInterceptor
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.ExpressApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideExpressApi(): ExpressApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.EXPRESS_API_BASE_URL)
            .client(
                OkHttpClient.Builder().build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpressApi::class.java)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @Provides
    fun provideDaDataHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideDaDataApi(daDataHttpClient: OkHttpClient): DaDataApi =
        Retrofit.Builder()
            .client(daDataHttpClient)
            .baseUrl(BuildConfig.DADATA_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DaDataApi::class.java)

    companion object {

        private const val CONNECTION_TIMEOUT = 10L
    }
}

package com.voxeldev.tinkofflab.di.modules

import com.voxeldev.tinkofflab.BuildConfig
import com.voxeldev.tinkofflab.data.network.dadataapi.DaDataRepositoryImpl
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.DaDataApi
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.interceptors.ApiKeyInterceptor
import com.voxeldev.tinkofflab.domain.repository.DaDataRepository
import com.voxeldev.tinkofflab.domain.usecases.GetAddressSuggestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class DaDataModule {

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
    fun provideHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.DADATA_API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideDaDataApi(retrofit: Retrofit): DaDataApi =
        retrofit.create(DaDataApi::class.java)

    @Provides
    fun provideDaDataRepository(daDataApi: DaDataApi): DaDataRepository =
        DaDataRepositoryImpl(daDataApi)

    // todo: move somewhere else if required
    @Provides
    fun provideGetAddressSuggestionsUseCase(
        repository: DaDataRepository
    ): GetAddressSuggestionsUseCase =
        GetAddressSuggestionsUseCase(repository)

    companion object {
        private const val CONNECTION_TIMEOUT = 10L
    }
}

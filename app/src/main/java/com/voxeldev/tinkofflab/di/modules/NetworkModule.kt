package com.voxeldev.tinkofflab.di.modules

import com.voxeldev.tinkofflab.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.EXPRESS_API_BASE_URL)
            .client(
                OkHttpClient.Builder().build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

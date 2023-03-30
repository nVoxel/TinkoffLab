package com.voxeldev.tinkofflab.data.network.dadataapi.datasource.interceptors

import com.voxeldev.tinkofflab.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newHeaders = original.headers.newBuilder()
            .add("Authorization", "Token ${BuildConfig.DADATA_API_KEY}")
            .add("Content-Type", "application/json")
            .add("Accept", "application/json")
            .build()
        return chain.proceed(
            original.newBuilder()
                .headers(newHeaders)
                .build()
        )
    }
}

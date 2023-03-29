package com.voxeldev.tinkofflab.data.network.dadataapi.datasource

import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.requests.QueryRequest
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses.SuggestionsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DaDataApi {

    @POST(".")
    fun getSuggestions(
        @Body query: QueryRequest
    ): Call<SuggestionsResponse>
}

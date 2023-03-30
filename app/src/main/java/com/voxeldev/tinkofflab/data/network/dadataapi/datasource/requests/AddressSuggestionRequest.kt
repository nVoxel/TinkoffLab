package com.voxeldev.tinkofflab.data.network.dadataapi.datasource.requests

import com.google.gson.annotations.SerializedName

data class AddressSuggestionRequest(
    @SerializedName("query")
    val query: String,
    @SerializedName("language")
    val language: String
)

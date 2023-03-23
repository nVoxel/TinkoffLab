package com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses


import com.google.gson.annotations.SerializedName

data class SuggestionResponse(
    @SerializedName("data")
    val data: DataResponse,
    @SerializedName("unrestricted_value")
    val unrestrictedValue: String?,
    @SerializedName("value")
    val value: String?
)

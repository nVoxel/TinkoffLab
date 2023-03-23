package com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses

import com.google.gson.annotations.SerializedName

data class AddressSearchResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("lat")
    val lat: Float?,
    @SerializedName("lon")
    val lon: Float?
)

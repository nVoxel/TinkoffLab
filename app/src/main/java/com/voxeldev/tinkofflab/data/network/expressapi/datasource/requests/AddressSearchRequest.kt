package com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests

import com.google.gson.annotations.SerializedName

data class AddressSearchRequest(
    @SerializedName("address")
    val address: String?,
    @SerializedName("lat")
    val lat: Float?,
    @SerializedName("lon")
    val lon: Float?
)

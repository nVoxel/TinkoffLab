package com.voxeldev.tinkofflab.data.network.expressapi.datasource.models

import com.google.gson.annotations.SerializedName

data class AddressApiModel(
    @SerializedName("address")
    val address: String?,
    @SerializedName("lat")
    val lat: Float?,
    @SerializedName("lon")
    val lon: Float?
)

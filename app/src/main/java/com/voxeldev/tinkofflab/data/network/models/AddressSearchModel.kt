package com.voxeldev.tinkofflab.data.network.models

import com.google.gson.annotations.SerializedName

data class AddressSearchModel(
    @SerializedName("address")
    val address: String?,
    @SerializedName("lat")
    val lat: Float?,
    @SerializedName("lon")
    val lon: Float?
)

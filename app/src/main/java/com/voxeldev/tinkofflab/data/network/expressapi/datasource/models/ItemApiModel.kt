package com.voxeldev.tinkofflab.data.network.expressapi.datasource.models

import com.google.gson.annotations.SerializedName

data class ItemApiModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?
)

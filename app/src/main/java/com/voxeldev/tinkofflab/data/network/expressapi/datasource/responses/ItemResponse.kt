package com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?
)

package com.voxeldev.tinkofflab.data.network.models

import com.google.gson.annotations.SerializedName

data class ItemModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?
)

package com.voxeldev.tinkofflab.data.network.errors

import com.google.gson.annotations.SerializedName

data class ValidationError(
    // val loc : List<String or Int>
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("type")
    val type: String?
)

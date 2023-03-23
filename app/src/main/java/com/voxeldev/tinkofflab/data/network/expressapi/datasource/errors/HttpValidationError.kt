package com.voxeldev.tinkofflab.data.network.expressapi.datasource.errors

import com.google.gson.annotations.SerializedName

data class HttpValidationError(
    @SerializedName("detail")
    val detail: List<ValidationError>?
)

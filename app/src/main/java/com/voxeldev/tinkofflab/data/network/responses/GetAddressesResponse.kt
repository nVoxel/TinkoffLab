package com.voxeldev.tinkofflab.data.network.responses

import com.google.gson.annotations.SerializedName
import com.voxeldev.tinkofflab.data.network.models.AddressModel

data class GetAddressesResponse(
    @SerializedName("")
    val addresses: List<AddressModel>
)

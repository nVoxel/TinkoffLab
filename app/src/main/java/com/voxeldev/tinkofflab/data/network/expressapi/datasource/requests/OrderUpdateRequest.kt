package com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests

import com.google.gson.annotations.SerializedName
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.AddressApiModel
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.TimeSlotApiModel

data class OrderUpdateRequest(
    @SerializedName("point")
    val address: AddressApiModel,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotApiModel,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("status")
    val status: String
)

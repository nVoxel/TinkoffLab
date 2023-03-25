package com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests

import com.google.gson.annotations.SerializedName
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.AddressResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.ItemResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.TimeSlotResponse

data class OrderCreateRequest(
    @SerializedName("point")
    val address: AddressResponse,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotResponse,
    @SerializedName("items")
    val items: List<ItemResponse>,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("status")
    val status: String
)

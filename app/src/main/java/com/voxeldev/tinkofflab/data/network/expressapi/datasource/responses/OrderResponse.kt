package com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("point")
    val address: AddressResponse?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotResponse?,
    @SerializedName("items")
    val items: List<ItemResponse>?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("id")
    val id: Int?
)

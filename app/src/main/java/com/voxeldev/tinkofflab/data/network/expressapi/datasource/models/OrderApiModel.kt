package com.voxeldev.tinkofflab.data.network.expressapi.datasource.models

import com.google.gson.annotations.SerializedName

data class OrderApiModel(
    @SerializedName("point")
    val address: AddressApiModel?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotApiModel?,
    @SerializedName("items")
    val items: List<ItemApiModel>?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("id")
    val id: Int?
)

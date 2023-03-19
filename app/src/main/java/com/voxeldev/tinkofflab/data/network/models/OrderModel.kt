package com.voxeldev.tinkofflab.data.network.models

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("address")
    val address: AddressModel?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotModel?,
    @SerializedName("items")
    val items: List<ItemModel>?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("id")
    val id: Int?
)

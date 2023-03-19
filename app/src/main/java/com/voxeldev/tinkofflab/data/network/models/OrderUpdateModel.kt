package com.voxeldev.tinkofflab.data.network.models

import com.google.gson.annotations.SerializedName

data class OrderUpdateModel(
    @SerializedName("address")
    val address: AddressModel?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotModel?,
    @SerializedName("comment")
    val comment: String?
)

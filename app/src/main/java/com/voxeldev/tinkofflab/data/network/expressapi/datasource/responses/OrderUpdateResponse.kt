package com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses

import com.google.gson.annotations.SerializedName

data class OrderUpdateResponse(
    @SerializedName("address")
    val address: AddressResponse?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("delivery_slot")
    val deliverySlot: TimeSlotResponse?,
    @SerializedName("comment")
    val comment: String?
)

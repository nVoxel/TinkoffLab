package com.voxeldev.tinkofflab.data.network.responses

import com.google.gson.annotations.SerializedName
import com.voxeldev.tinkofflab.data.network.models.OrderModel

data class GetOrdersResponse (
    @SerializedName("")
    val orders: List<OrderModel>?
)

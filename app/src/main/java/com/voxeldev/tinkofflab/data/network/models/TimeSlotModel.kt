package com.voxeldev.tinkofflab.data.network.models

import com.google.gson.annotations.SerializedName

data class TimeSlotModel(
    @SerializedName("date")
    val date: String?,
    @SerializedName("time_from")
    val timeFrom: String?,
    @SerializedName("time_to")
    val timeTo: String?
)

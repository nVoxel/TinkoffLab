package com.voxeldev.tinkofflab.data.network.responses

import com.google.gson.annotations.SerializedName
import com.voxeldev.tinkofflab.data.network.models.TimeSlotModel

data class GetSlotsResponse(
    @SerializedName("")
    val slots: List<TimeSlotModel>
)

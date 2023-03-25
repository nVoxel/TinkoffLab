package com.voxeldev.tinkofflab.data.mappers

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.TimeSlotResponse
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeSlotMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(timeSlot: TimeSlotResponse): TimeSlotModel =
        TimeSlotModel(
            timeSlot.date!!,
            timeSlot.timeFrom!!,
            timeSlot.timeTo!!
        )

    fun toResponse(timeSlot: TimeSlotModel): TimeSlotResponse =
        TimeSlotResponse(
            timeSlot.date,
            timeSlot.timeFrom,
            timeSlot.timeTo
        )
}

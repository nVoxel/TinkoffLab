package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.TimeSlotApiModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeSlotMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(timeSlot: TimeSlotApiModel): TimeSlotModel =
        TimeSlotModel(
            timeSlot.date!!,
            timeSlot.timeFrom!!,
            timeSlot.timeTo!!
        )

    fun toApiModel(timeSlot: TimeSlotModel): TimeSlotApiModel =
        TimeSlotApiModel(
            timeSlot.date,
            timeSlot.timeFrom,
            timeSlot.timeTo
        )
}

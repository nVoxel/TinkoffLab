package com.voxeldev.tinkofflab.domain.models.expressapi

import android.content.res.Resources
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import java.time.LocalDate

data class TimeSlotModel(
    val date: String,
    val timeFrom: String,
    val timeTo: String
) {

    fun toString(resources: Resources): String {
        val currentDate = LocalDate.now()
        var displayDate: String = date

        runCatching { LocalDate.parse(date) }
            .onSuccess { displayDate = it.toRelativeString(currentDate, resources) }

        return resources.getString(
            R.string.order_datetime_placeholder,
            displayDate, timeFrom, timeTo
        )
    }
}

package com.voxeldev.tinkofflab.ui.utils

import android.content.res.Resources
import com.voxeldev.tinkofflab.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.util.*


fun LocalDate.generateOrderedDates(size: Int): List<LocalDate> =
    (0 until size).map { this.plusDays(it.toLong()) }

fun LocalDate.toRelativeString(
    startDate: LocalDate,
    resources: Resources,
    dateTimeFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
        .appendPattern("d MMMM")
        .toFormatter(Locale.getDefault())
): String {
    return when (ChronoUnit.DAYS.between(startDate, this)) {
        0L -> resources.getString(R.string.orders_date_today)
        1L -> resources.getString(R.string.orders_date_tomorrow)
        2L -> resources.getString(R.string.orders_date_in_one_day)
        else -> this.format(dateTimeFormatter)
    }
}

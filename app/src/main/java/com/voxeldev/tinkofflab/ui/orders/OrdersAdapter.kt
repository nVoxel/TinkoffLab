package com.voxeldev.tinkofflab.ui.orders

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.ItemOrderBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit


class OrdersAdapter(
    private val orders: List<OrderModel>
) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(orders[position])

    override fun getItemCount() = orders.size

    inner class ViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel) {
            with(binding) {
                cardviewOrder.setOnClickListener {
                    // TODO: Open order details
                }
                textviewDatetime.text =
                    timeSlotToString(binding.root.resources, order.deliverySlot)
            }
        }

        private fun timeSlotToString(resources: Resources, timeSlot: TimeSlotModel): String {
            val currentDate = LocalDate.now()
            val timeSlotDate = LocalDate.parse(timeSlot.date, DateTimeFormatter.ISO_DATE)

            val dateString = when (ChronoUnit.DAYS.between(currentDate, timeSlotDate)) {
                0L -> resources.getString(R.string.orders_date_today)
                1L -> resources.getString(R.string.orders_date_tomorrow)
                2L -> resources.getString(R.string.orders_date_in_one_day)
                else -> timeSlotDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            }

            return resources.getString(
                R.string.order_datetime_placeholder,
                dateString,
                timeSlot.timeFrom,
                timeSlot.timeTo
            )
        }
    }
}

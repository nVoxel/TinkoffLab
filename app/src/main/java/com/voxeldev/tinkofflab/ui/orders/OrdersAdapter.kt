package com.voxeldev.tinkofflab.ui.orders

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.ItemOrderBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import java.time.LocalDate


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

            runCatching {
                LocalDate.parse(timeSlot.date)
            }.onSuccess {
                return resources.getString(
                    R.string.order_datetime_placeholder,
                    it.toRelativeString(currentDate, resources),
                    timeSlot.timeFrom,
                    timeSlot.timeTo
                )
            }

            return timeSlot.date
        }
    }
}

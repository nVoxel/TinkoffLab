package com.voxeldev.tinkofflab.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.databinding.ItemOrderBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel


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
                textviewDatetime.text = timeSlotToString(order.deliverySlot)
            }
        }

        private fun timeSlotToString(timeSlot: TimeSlotModel): String {
            TODO()
        }
    }
}

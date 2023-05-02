package com.voxeldev.tinkofflab.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.databinding.ItemOrderBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel


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
                textviewDatetime.text = order.deliverySlot.toString(binding.root.resources)
            }
        }
    }
}

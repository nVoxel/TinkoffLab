package com.voxeldev.tinkofflab.ui.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.databinding.ItemDeliveryTypeBinding
import com.voxeldev.tinkofflab.ui.utils.setOnPressedAnim

class DeliveryTypeAdapter : ListAdapter<DeliveryTypeModel, DeliveryTypeAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<DeliveryTypeModel>() {
        override fun areItemsTheSame(
            oldItem: DeliveryTypeModel,
            newItem: DeliveryTypeModel
        ): Boolean = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: DeliveryTypeModel,
            newItem: DeliveryTypeModel
        ): Boolean = oldItem == newItem

    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemDeliveryTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    class ViewHolder(
        private val binding: ItemDeliveryTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnPressedAnim()
        }

        fun bind(deliveryType: DeliveryTypeModel) {
            with(binding) {
                tvDeliveryType.text = root.context.getString(deliveryType.name)
                ivDeliveryTypeIcon.setImageResource(deliveryType.icon)
                root.setOnClickListener {
                    deliveryType.onClick()
                }
            }
        }

    }

}

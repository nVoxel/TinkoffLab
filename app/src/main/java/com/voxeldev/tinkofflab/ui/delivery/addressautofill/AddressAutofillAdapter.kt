package com.voxeldev.tinkofflab.ui.delivery.addressautofill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.databinding.ItemAddressSuggestionBinding
import com.voxeldev.tinkofflab.domain.models.AddressModel
import com.voxeldev.tinkofflab.ui.utils.OnAddressClick

class AddressAutofillAdapter(
    private val onAddressClick: OnAddressClick
) : ListAdapter<AddressModel, AddressAutofillAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<AddressModel>() {
        override fun areItemsTheSame(
            oldItem: AddressModel,
            newItem: AddressModel
        ): Boolean = oldItem.fullAddress == newItem.fullAddress

        override fun areContentsTheSame(
            oldItem: AddressModel,
            newItem: AddressModel
        ): Boolean = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAddressSuggestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemAddressSuggestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: AddressModel) {
            with(binding) {
                textviewItemAddressFull.text = address.fullAddress
                textviewItemAddressStreet.text = address.street
                root.setOnClickListener {
                    onAddressClick(address.fullAddress)
                }
            }
        }
    }
}

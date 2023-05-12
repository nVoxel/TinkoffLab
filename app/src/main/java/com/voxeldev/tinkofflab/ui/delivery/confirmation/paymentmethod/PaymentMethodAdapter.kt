package com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.databinding.ItemPaymentBinding

class PaymentMethodAdapter(
    private var list: List<PaymentMethodModel>,
    private val onClick: (PaymentMethod) -> Unit,
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    private var checkedItemPosition: Int? = list.indexOfFirst { it.isChecked }.takeIf { it != -1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    private fun onItemClicked(position: Int) {
        checkedItemPosition?.let {
            list[it].isChecked = false
            notifyItemChanged(it)
        }
        checkedItemPosition = position
        list[position].isChecked = true
        notifyItemChanged(position)
    }

    inner class ViewHolder(
        private val binding: ItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(paymentMethod: PaymentMethodModel, position: Int) {
            with(binding) {
                tvMethod.text = paymentMethod.name
                ivCheck.isVisible = paymentMethod.isChecked
                root.setOnClickListener {
                    onItemClicked(position)
                    onClick(paymentMethod.type)
                }
            }
        }
    }
}

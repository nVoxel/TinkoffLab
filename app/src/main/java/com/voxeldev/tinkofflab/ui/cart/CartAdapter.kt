package com.voxeldev.tinkofflab.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.ItemCartBinding

class CartAdapter(
    private var items: List<CartItemModel>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items = listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemModel) {
            with(binding) {
                textviewItemTitle.text = item.itemModel.name
                root.resources.let {
                    textviewItemPrice.text = it.getString(
                        R.string.price_placeholder,
                        item.itemModel.price * item.count
                    )
                    textviewItemPriceCross.text = it.getString(
                        R.string.price_cross_placeholder,
                        item.itemModel.price
                    )

                    chipCount.text = it.getString(
                        R.string.item_count_placeholder,
                        item.count
                    )
                }

                item.itemModel.imageUrl?.let { TODO() }
                item.itemModel.imageRes?.let {
                    imageviewItem.setImageDrawable(
                        AppCompatResources.getDrawable(root.context, it)
                    )
                }
            }
        }
    }
}

package com.voxeldev.tinkofflab.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.ItemCatalogBinding
import com.voxeldev.tinkofflab.ui.cart.CartItemModel

class CatalogAdapter(
    private val items: List<CartItemModel>
) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCatalogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemCatalogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemModel) {
            with(binding) {
                textviewItemName.text = item.itemModel.name
                textviewItemPrice.text = root.resources.getString(
                    R.string.price_placeholder,
                    item.itemModel.price
                )

                item.itemModel.imageUrl?.let { TODO() }
                item.itemModel.imageRes?.let {
                    imageviewItem.setImageDrawable(
                        AppCompatResources.getDrawable(root.context, it)
                    )
                }

                textviewCounter.text = item.count.toString()
                toggleVisibility(this, item)

                imagebuttonAdd.setOnClickListener {
                    item.count++
                    textviewCounter.text = item.count.toString()
                    toggleVisibility(this, item)
                }

                imagebuttonRemove.setOnClickListener {
                    item.count--
                    textviewCounter.text = item.count.toString()
                    toggleVisibility(this, item)
                }
            }
        }

        private fun toggleVisibility(binding: ItemCatalogBinding, item: CartItemModel) {
            with(binding) {
                when (item.count) {
                    0 -> {
                        textviewCounter.isInvisible = true
                        imagebuttonRemove.isInvisible = true
                    }

                    else -> {
                        textviewCounter.isInvisible = false
                        imagebuttonRemove.isInvisible = false
                    }
                }
            }
        }
    }
}

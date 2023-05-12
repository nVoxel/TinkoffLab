package com.voxeldev.tinkofflab.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentCartBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val sharedOrderViewModel by activityViewModels<SharedOrderViewModel>()

    private lateinit var cartItemsFiltered: List<CartItemModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartItemsFiltered =
            sharedOrderViewModel.cartItems.value?.filter { it.count > 0 } ?: listOf()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            toolbar.setEndIconClickListener {
                sharedOrderViewModel.clearItems()
                cartItemsFiltered = listOf()
                (recyclerviewCartItems.adapter as? CartAdapter)?.clear()
                updateViews()
            }

            recyclerviewCartItems.adapter = CartAdapter(cartItemsFiltered)
            updateViews()

            buttonMakeOrder.setOnClickListener { makeOrderButtonClicked() }
        }
    }

    private fun updateViews() {
        binding?.run {
            itemsCountsSum().let {
                textviewItemsCount.text = if (it != 0) resources.getString(
                    R.string.cart_items_count_placeholder, it,
                    resources.getQuantityString(R.plurals.items, it)
                ) else
                    resources.getString(R.string.empty_cart_title)
                cardviewMakeOrder.isVisible = it > 0
            }

            textviewTotalCost.text = resources.getString(
                R.string.price_placeholder,
                itemsTotalSum()
            )
        }
    }

    private fun itemsCountsSum() = cartItemsFiltered.sumOf { it.count }

    private fun itemsTotalSum() = cartItemsFiltered.sumOf { it.itemModel.price * it.count }

    private fun makeOrderButtonClicked() {
        sharedOrderViewModel.setItems(cartItemsFiltered)
        App.router.navigateTo(Screens.DeliveryType())
    }
}

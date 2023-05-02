package com.voxeldev.tinkofflab.ui.cart

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentCartBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val sharedOrderViewModel by activityViewModels<SharedOrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding?.run {
            buttonMakeOrder.setOnClickListener { makeOrderButtonClicked() }
            return root
        } ?: return null
    }

    private fun makeOrderButtonClicked() {
        sharedOrderViewModel.setItems(getCartItems(resources))
        App.router.navigateTo(Screens.DeliveryType())
    }

    companion object {

        private fun getCartItems(resources: Resources) =
            listOf(ItemModel(resources.getString(R.string.cart_item_title_text), CART_ITEM_PRICE))

        private const val CART_ITEM_PRICE = 3556
    }
}

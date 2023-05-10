package com.voxeldev.tinkofflab.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.databinding.FragmentCatalogBinding
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.cart.CartItemModel
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : BaseFragment<FragmentCatalogBinding>() {

    private val sharedOrderViewModel by activityViewModels<SharedOrderViewModel>()
    private val catalogViewModel: CatalogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)

        with(sharedOrderViewModel) {
            observe(cartItems, ::handleCartItems)
        }
        with(catalogViewModel) {
            observe(items, ::handleCatalogItems)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedOrderViewModel.cartItems.value == null)
            catalogViewModel.getCatalogItems()
    }

    private fun handleCartItems(items: List<CartItemModel>?) {
        items?.let { binding?.run { recyclerviewCatalog.adapter = getAdapter(items) } }
    }

    private fun handleCatalogItems(items: List<CatalogItemModel>?) {
        items?.let { sharedOrderViewModel.setItems(items.map { CartItemModel(it) }) }
    }

    private fun getAdapter(items: List<CartItemModel>) = CatalogAdapter(items)
}

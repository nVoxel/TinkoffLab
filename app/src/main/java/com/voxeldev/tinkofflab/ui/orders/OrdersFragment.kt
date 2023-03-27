package com.voxeldev.tinkofflab.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentOrdersBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    private val ordersViewModel: OrdersViewModel by viewModels()

    private var orders: List<OrderModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        with (ordersViewModel) {
            observe(orders, ::handleOrders)
        }

        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersViewModel.getOrders()
    }

    private fun handleOrders(orders: List<OrderModel>?) {
        orders?.let {
            this.orders = it
            setupRecyclerView()
        } ?: showSnackbar(R.string.orders_get_error)
    }

    private fun setupRecyclerView() {
        orders?.let {
            binding?.recyclerviewOrders?.adapter = OrdersAdapter(it)
        }
    }
}

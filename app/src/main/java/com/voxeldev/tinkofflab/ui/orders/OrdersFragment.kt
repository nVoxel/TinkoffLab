package com.voxeldev.tinkofflab.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentOrdersBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    private val ordersViewModel: OrdersViewModel by viewModels()
    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    private var orders: List<OrderModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        with(ordersViewModel) {
            observe(orders, ::handleOrders)
            observe(exception, ::handleException)
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

    private fun handleException(exception: Exception?) {
        showSnackbar(R.string.orders_get_error)
        Log.e(LOG_TAG, exception?.stackTraceToString() ?: "Unknown error")
    }

    private fun setupRecyclerView() {
        orders?.let { list ->
            binding?.recyclerviewOrders?.adapter = OrdersAdapter(list) {
                adapterItemOnClickListener(it)
            }
        }
    }

    private fun adapterItemOnClickListener(orderModel: OrderModel) {
        sharedOrderViewModel.run {
            orderEditModeEnabled = true
            setOrder(orderModel)
        }

        App.router.navigateTo(Screens.Confirmation())
    }

    companion object {
        private const val LOG_TAG = "OrdersFragment"
    }
}

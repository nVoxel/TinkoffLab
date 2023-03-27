package com.voxeldev.tinkofflab.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.ui.cart.CartFragment
import com.voxeldev.tinkofflab.ui.delivery.deliverytype.DeliveryTypeFragment
import com.voxeldev.tinkofflab.ui.orders.OrdersFragment

@Suppress("FunctionName")
object Screens {

    fun Cart() = FragmentScreen { CartFragment() }
    fun DeliveryType() = FragmentScreen { DeliveryTypeFragment() }
    fun Orders() = FragmentScreen { OrdersFragment() }
}

package com.voxeldev.tinkofflab.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.ui.cart.CartFragment
import com.voxeldev.tinkofflab.ui.delivery.AddressAFragment
import com.voxeldev.tinkofflab.ui.delivery.AddressBFragment
import com.voxeldev.tinkofflab.ui.delivery.ConfirmationFragment
import com.voxeldev.tinkofflab.ui.delivery.DeliveryTypeFragment
import com.voxeldev.tinkofflab.ui.delivery.PlacedOrderFragment
import com.voxeldev.tinkofflab.ui.delivery.SlotsChooserFragment
import com.voxeldev.tinkofflab.ui.orders.OrderDetailsFragment
import com.voxeldev.tinkofflab.ui.orders.OrdersFragment

@Suppress("FunctionName")
object Screens {
    // Cart chain
    fun Cart() = FragmentScreen { CartFragment() }
    fun DeliveryType() = FragmentScreen { DeliveryTypeFragment() }
    fun AddressA() = FragmentScreen { AddressAFragment() }
    fun AddressB() = FragmentScreen { AddressBFragment() }
    fun SlotsChooser() = FragmentScreen { SlotsChooserFragment() }
    fun Confirmation() = FragmentScreen { ConfirmationFragment() }
    fun PlacedOrder() = FragmentScreen { PlacedOrderFragment() }

    // Orders chain
    fun Orders() = FragmentScreen { OrdersFragment() }
    fun OrderDetails() = FragmentScreen { OrderDetailsFragment() }


}

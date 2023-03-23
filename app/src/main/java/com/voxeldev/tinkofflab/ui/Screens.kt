package com.voxeldev.tinkofflab.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.ui.cart.CartFragment
import com.voxeldev.tinkofflab.ui.delivery.deliverytype.DeliveryTypeFragment

@Suppress("FunctionName")
object Screens {

    fun DeliveryType() = FragmentScreen { DeliveryTypeFragment() }
    fun Cart() = FragmentScreen { CartFragment() }
}

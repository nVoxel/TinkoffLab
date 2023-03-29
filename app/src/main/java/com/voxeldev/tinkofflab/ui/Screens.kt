package com.voxeldev.tinkofflab.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.ui.cart.CartFragment
import com.voxeldev.tinkofflab.ui.delivery.addressautofill.AddressAutofillFragment
import com.voxeldev.tinkofflab.ui.delivery.deliverytype.DeliveryTypeFragment
import com.voxeldev.tinkofflab.ui.delivery.onboarding.OnboardingFragment
import com.voxeldev.tinkofflab.ui.orders.OrdersFragment

@Suppress("FunctionName")
object Screens {

    fun Cart() = FragmentScreen { CartFragment() }

    fun DeliveryType() = FragmentScreen { DeliveryTypeFragment() }

    fun Onboarding() = FragmentScreen { OnboardingFragment() }

    fun Orders() = FragmentScreen { OrdersFragment() }

    fun AddressAutofill() = FragmentScreen { AddressAutofillFragment() }
}

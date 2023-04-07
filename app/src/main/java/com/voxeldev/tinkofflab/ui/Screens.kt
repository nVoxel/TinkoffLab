package com.voxeldev.tinkofflab.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.voxeldev.tinkofflab.ui.cart.CartFragment
import com.voxeldev.tinkofflab.ui.delivery.addressautofill.AddressAutofillFragment
import com.voxeldev.tinkofflab.ui.delivery.addressmanual.AddressManualFragment
import com.voxeldev.tinkofflab.ui.delivery.appointment.AppointmentFragment
import com.voxeldev.tinkofflab.ui.delivery.deliverytype.DeliveryTypeFragment
import com.voxeldev.tinkofflab.ui.delivery.onboarding.OnboardingFragment
import com.voxeldev.tinkofflab.ui.delivery.orderplaced.OrderPlacedFragment
import com.voxeldev.tinkofflab.ui.orders.OrdersFragment
import com.voxeldev.tinkofflab.ui.toggleaddress.ToggleAddressFragment

@Suppress("FunctionName")
object Screens {

    fun ToggleAddress() = FragmentScreen { ToggleAddressFragment() }

    fun AddressAutofill() = FragmentScreen { AddressAutofillFragment() }

    fun AddressManual() = FragmentScreen { AddressManualFragment() }

    fun Appointment() = FragmentScreen { AppointmentFragment() }

    fun Cart() = FragmentScreen { CartFragment() }

    fun DeliveryType() = FragmentScreen { DeliveryTypeFragment() }

    fun Onboarding() = FragmentScreen { OnboardingFragment() }

    fun OrderPlaced() = FragmentScreen { OrderPlacedFragment() }

    fun Orders() = FragmentScreen { OrdersFragment() }
}

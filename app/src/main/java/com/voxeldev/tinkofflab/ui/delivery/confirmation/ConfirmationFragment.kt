package com.voxeldev.tinkofflab.ui.delivery.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentConfirmationBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod.PaymentMethod
import com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod.PaymentMethodBottomSheet
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    private val confirmationViewModel: ConfirmationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeViewModel()
    }

    private fun setClickListeners() {
        binding?.run {
            tvChangeDelivery.setOnClickListener {
                App.router.exit()
            }
            tvChangePaymentMethod.setOnClickListener {
                PaymentMethodBottomSheet().show(childFragmentManager, null)
            }
            buttonContinue.setOnClickListener {
                sharedOrderViewModel.apply {
                    setStatus("NEW")
                    setPaymentMethod(paymentMethod.value ?: error("impossible payment method"))
                }.getOrder()?.let {
                    confirmationViewModel.createOrder(/*it*/)
                }
            }
        }
    }

    private fun observeViewModel() {
        with(sharedOrderViewModel) {
            observe(sharedAddress) {
                if (it == null) return@observe
                binding?.tvDeliveryAddress?.text = it.address
            }
            observe(deliverySlot, ::handleDeliverySlot)
            observe(paymentMethod, ::handlePaymentMethod)
        }
        with(confirmationViewModel) {
            observe(loading) {
                binding?.buttonContinue?.showLoading = it == true
            }
            observe(orderCreationSuccess) {
                if (it == null) return@observe
                App.router.newRootChain(Screens.OrderPlaced())
            }
            observe(exception) {
                showSnackbar(R.string.order_creation_error)
            }
        }
    }

    private fun handleDeliverySlot(address: TimeSlotModel?) {
        if (address == null) return
        val day = LocalDate
            .parse(address.date, DateTimeFormatter.ISO_DATE)
            .toRelativeString(LocalDate.now(), resources)
        val result = getString(
            R.string.time_range,
            day, address.timeFrom, address.timeTo
        )
        binding?.tvDeliveryTime?.text = result
    }

    private fun handlePaymentMethod(method: PaymentMethod?) {
        if (method == null) return
        binding?.tvPaymentMethod?.text = getString(
            when (method) {
                PaymentMethod.CARD -> R.string.card_payment
                PaymentMethod.CASH -> R.string.cash_payment
            }
        )
    }
}

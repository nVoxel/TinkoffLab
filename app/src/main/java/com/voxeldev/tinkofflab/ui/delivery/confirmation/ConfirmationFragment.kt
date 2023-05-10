package com.voxeldev.tinkofflab.ui.delivery.confirmation

import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentConfirmationBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod.PaymentMethod
import com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod.PaymentMethodBottomSheet
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.ui.views.OrderCanceledDialog
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    private val confirmationViewModel: ConfirmationViewModel by viewModels()

    private val orderEditModeEnabled: Boolean by lazy { sharedOrderViewModel.orderEditModeEnabled }

    companion object {
        private const val ORDER_CANCELED_DIALOG_DELAY_MILLIS = 3000L
        private const val ORDER_CANCELLATION_ALERT_TEXT_SIZE_SP = 20F
    }

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

        if (orderEditModeEnabled) setOrderEditMode()
    }

    private fun setClickListeners() {
        binding?.run {
            tvChangeDelivery.setOnClickListener {
                if (orderEditModeEnabled) App.router.navigateTo(Screens.Appointment())
                else App.router.exit()
            }
            tvChangePaymentMethod.setOnClickListener {
                if (orderEditModeEnabled) setPaymentMethodResultListener()
                PaymentMethodBottomSheet().show(childFragmentManager, null)
            }
            buttonContinue.setOnClickListener { continueButtonOnClick() }
        }
    }

    override fun onResume() {
        super.onResume()

        // used to fix disappearing delivery slot and payment method
        // when navigating back from appointment fragment
        with(sharedOrderViewModel) {
            if (!orderEditModeEnabled) return@with
            handleDeliverySlot(deliverySlot.value!!)
            handlePaymentMethod(paymentMethod.value!!)
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
                sharedOrderViewModel.apply {
                    createdOrder = it
                    resetAddress()
                }
                App.router.newRootChain(
                    Screens.HostFragment(R.id.item_orders),
                    Screens.OrderPlaced()
                )
            }
            observe(orderUpdateSuccess) {
                if (it == null) return@observe
                App.router.newRootScreen(Screens.HostFragment(R.id.item_orders))
            }
            observe(orderCancelSuccess) {
                if (it == null) return@observe
                App.router.newRootScreen(Screens.HostFragment(R.id.item_orders))
                OrderCanceledDialog(requireContext()).run {
                    show()
                    view?.postDelayed({ hide() }, ORDER_CANCELED_DIALOG_DELAY_MILLIS)
                }
            }
            observe(exception) {
                showAlertDialog(
                    if (orderEditModeEnabled) R.string.order_update_error
                    else R.string.order_creation_error
                )
            }
        }
    }

    private fun setOrderEditMode() {
        binding?.run {
            buttonContinue.run {
                setView(alternativeView = true)
                setText(resources.getString(R.string.order_cancel))
            }
        }
    }

    private fun continueButtonOnClick() {
        if (orderEditModeEnabled) {
            showOrderCancellationAlert()
            return
        }

        sharedOrderViewModel.apply {
            setStatus(OrderModel.NEW_ORDER_STATUS)
            setPaymentMethod(paymentMethod.value ?: error("impossible payment method"))
        }.getOrder()?.let {
            confirmationViewModel.createOrder(it)
        }
    }

    private fun setPaymentMethodResultListener() {
        val previousPaymentMethod = sharedOrderViewModel.paymentMethod.value

        childFragmentManager.setFragmentResultListener(
            PaymentMethodBottomSheet.DIALOG_CANCEL_RESULT_KEY,
            viewLifecycleOwner
        ) { _, _ ->
            if (previousPaymentMethod == sharedOrderViewModel.paymentMethod.value)
                return@setFragmentResultListener

            sharedOrderViewModel.getOrder()?.let {
                confirmationViewModel.updateOrder(it)
            }
        }
    }

    private fun showOrderCancellationAlert() {
        AlertDialog.Builder(requireContext(), R.style.CancelAlertDialog)
            .setMessage(R.string.order_cancel_confirmation)
            .setPositiveButton(R.string.order_cancel_yes) { _, _ ->
                sharedOrderViewModel.apply {
                    setStatus(OrderModel.CANCELED_ORDER_STATUS)
                }.getOrder()?.let {
                    confirmationViewModel.updateOrder(it, cancelOrder = true)
                }
            }
            .setNegativeButton(R.string.order_cancel_no) { _, _ -> }
            .show()
            .apply {
                findViewById<TextView>(android.R.id.message).apply {
                    setTextColor(resources.getColor(R.color.alert_dialog_text, context.theme))
                    textSize = ORDER_CANCELLATION_ALERT_TEXT_SIZE_SP
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                }
            }
    }

    private fun handleDeliverySlot(address: TimeSlotModel?) {
        if (address == null) return
        runCatching {
            LocalDate.parse(address.date, DateTimeFormatter.ISO_DATE)
                .toRelativeString(LocalDate.now(), resources)
        }
            .onSuccess {
                val result = getString(
                    R.string.time_range,
                    it, address.timeFrom, address.timeTo
                )
                binding?.tvDeliveryTime?.text = result
            }
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

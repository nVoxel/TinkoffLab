package com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentPaymentMethodBinding
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel

class PaymentMethodBottomSheet : BottomSheetDialogFragment() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    private var binding: FragmentPaymentMethodBinding? = null

    private val paymentMethods by lazy {
        val paymentMethod = sharedOrderViewModel.paymentMethod.value
        listOf(
            PaymentMethodModel(
                name = requireContext().getString(R.string.card_payment),
                type = PaymentMethod.CARD,
                isChecked = paymentMethod == PaymentMethod.CARD,
            ),
            PaymentMethodModel(
                name = requireContext().getString(R.string.cash_payment),
                type = PaymentMethod.CASH,
                isChecked = paymentMethod == PaymentMethod.CASH
            ),
        )
    }

    companion object {
        const val DIALOG_CANCEL_RESULT_KEY = "PAYMENT_METHOD"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentMethodBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvPaymentMethod?.adapter = PaymentMethodAdapter(paymentMethods) {
            sharedOrderViewModel.setPaymentMethod(it)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setFragmentResult(DIALOG_CANCEL_RESULT_KEY, bundleOf())
    }
}

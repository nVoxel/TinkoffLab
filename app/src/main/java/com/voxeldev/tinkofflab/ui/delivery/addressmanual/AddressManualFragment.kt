package com.voxeldev.tinkofflab.ui.delivery.addressmanual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAddressManualBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel

class AddressManualFragment : BaseFragment<FragmentAddressManualBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressManualBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            buttonDone.setOnClickListener {
                onDone()
            }
            edittextIndex.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onDone()
                }
                true
            }
        }
    }

    private fun onDone() {
        val data = listOf(
            binding?.edittextIndex?.text,
            binding?.edittextCountry?.text,
            binding?.edittextRegion?.text,
            binding?.edittextCity?.text,
            binding?.edittextStreet?.text,
            binding?.edittextHouse?.text
        )

        data
            .filterNot { it.isNullOrBlank() }
            .joinToString()
            .takeIf { it.isNotBlank() }
            ?.let {
                sharedOrderViewModel.setAddress(
                    ExpressAddressModel(
                        it,
                        // todo: fix
                        0f,
                        0f
                    )
                )
                App.router.exit()
            } ?: showSnackbar(R.string.empty_address_error)
    }
}

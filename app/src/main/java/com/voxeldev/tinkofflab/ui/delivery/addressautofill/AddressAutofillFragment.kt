package com.voxeldev.tinkofflab.ui.delivery.addressautofill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAddressAutofillBinding
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.DeliveryViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import com.voxeldev.tinkofflab.ui.utils.SpaceItemDecoration
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressAutofillFragment : BaseFragment<FragmentAddressAutofillBinding>() {

    private val deliveryViewModel by activityViewModels<DeliveryViewModel>()
    private val addressAutofillViewModel by viewModels<AddressAutofillViewModel>()

    private val adapter by lazy {
        AddressAutofillAdapter { address ->
            deliveryViewModel.setSharedOrderAddress(
                ExpressAddressModel(address, 0f, 0f)
            )

            App.router.navigateTo(Screens.Appointment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressAutofillBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setTextChangeListener()
        observeViewModel()
    }

    private fun setAdapter() {
        binding?.recyclerAddressAutofill?.also {
            it.addItemDecoration(SpaceItemDecoration(requireContext(), ITEM_DECORATION_SPACING))
            it.adapter = adapter
        }
    }

    private fun setTextChangeListener() {
        binding?.edittextAddress?.addTextChangedListener {
            addressAutofillViewModel.getSuggestions(it?.toString())
        }
    }

    private fun observeViewModel() {
        with(addressAutofillViewModel) {
            observe(suggestions, ::handleSuggestions)
            observe(loading, ::handleLoading)
            observe(exception, ::handleException)
        }
    }

    private fun handleSuggestions(suggestions: List<AddressModel>?) {
        suggestions?.let {
            binding?.textviewAddressNotFound?.isVisible = it.isEmpty()
            adapter.submitList(it)
        }
    }

    private fun handleLoading(isLoading: Boolean?) {
        binding?.shimmerAddressAutofill?.apply {
            if (isLoading == true)
                showShimmer(true)
            else
                hideShimmer()
        }
    }

    private fun handleException(exception: Exception?) {
        showSnackbar(R.string.address_get_error)
        Log.e(LOG_TAG, exception?.stackTraceToString() ?: "Unknown error")
    }

    companion object {

        private const val LOG_TAG = "AddressAutofillFragment"
        private const val ITEM_DECORATION_SPACING = 16f
    }
}

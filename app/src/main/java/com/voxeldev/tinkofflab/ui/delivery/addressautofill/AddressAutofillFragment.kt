package com.voxeldev.tinkofflab.ui.delivery.addressautofill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAddressAutofillBinding
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressAutofillFragment : BaseFragment<FragmentAddressAutofillBinding>() {

    private val sharedOrderViewModel by activityViewModels<SharedOrderViewModel>()
    private val addressAutofillViewModel by viewModels<AddressAutofillViewModel>()

    private val adapter by lazy { AddressAutofillAdapter { setAddress(it.fullAddress) } }

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
        binding?.recyclerAddressAutofill?.adapter = adapter
        setDoneListeners()
        setTextChangeListener()
        observeViewModel()
        setAddress(sharedOrderViewModel.sharedAddress.value?.address ?: "")
    }

    private fun setAddress(address: String) {
        binding?.edittextAddress?.apply {
            // to skip search
            addressAutofillViewModel.isNotAutofilled.set(false)
            setText(address)
            setSelection(address.length)
            binding?.textviewAddressNotFound?.isVisible = false
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

    private fun setDoneListeners() {
        binding?.apply {
            edittextAddress.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onDone()
                }
                true
            }
            buttonDone.setOnClickListener {
                onDone()
            }
        }
    }

    private fun onDone() {
        binding?.edittextAddress?.text
            ?.takeIf { it.isNotBlank() }
            ?.let {
                with(sharedOrderViewModel) {
                    setAddress(
                        // todo: fix lan and lon
                        ExpressAddressModel(it.toString(), 0f, 0f)
                    )
                    App.router.exit()
                }
            } ?: showSnackbar(R.string.empty_address_error)
    }

    private fun handleSuggestions(suggestions: List<AddressModel>?) {
        suggestions?.let {
            binding?.apply {
                val isNotAddressBlank = edittextAddress.text?.isNotBlank() ?: false
                textviewAddressNotFound.isVisible = it.isEmpty() && isNotAddressBlank
            }
            adapter.submitList(it)
        }
    }

    private fun handleLoading(isLoading: Boolean?) {
        binding?.loader?.cardviewLoader?.isVisible =
            isLoading == true && adapter.itemCount == 0
        binding?.shimmerAddressAutofill?.apply {
            if (isLoading == true)
                showShimmer(true)
            else
                hideShimmer()
        }
    }

    private fun handleException(exception: Exception?) {
        showAlertDialog(R.string.address_get_error)
        Log.e(LOG_TAG, exception?.stackTraceToString() ?: "Unknown error")
    }

    override fun onStart() {
        super.onStart()
        binding?.shimmerAddressAutofill?.hideShimmer()
    }

    override fun onResume() {
        super.onResume()
        addressAutofillViewModel.isFragmentPaused.set(false)
    }

    override fun onPause() {
        super.onPause()
        addressAutofillViewModel.isFragmentPaused.set(true)
    }

    companion object {

        private const val LOG_TAG = "AddressAutofillFragment"
    }
}

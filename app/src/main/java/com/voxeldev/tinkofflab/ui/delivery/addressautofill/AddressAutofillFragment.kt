package com.voxeldev.tinkofflab.ui.delivery.addressautofill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAddressAutofillBinding
import com.voxeldev.tinkofflab.ui.delivery.DeliveryViewModel
import com.voxeldev.tinkofflab.ui.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressAutofillFragment : Fragment(R.layout.fragment_address_autofill) {
    private lateinit var binding: FragmentAddressAutofillBinding

    private val deliveryViewModel by activityViewModels<DeliveryViewModel>()

    private val adapter by lazy { AddressAutofillAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressAutofillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setTextChangeListener()
        observeViewModel()
    }

    private fun setAdapter() {
        binding.recyclerAddressAutofill.also {
            it.addItemDecoration(SpaceItemDecoration(requireContext(), ITEM_DECORATION_SPACING))
            it.adapter = adapter
        }
    }

    private fun setTextChangeListener() {
        binding.edittextAddress.addTextChangedListener {
            deliveryViewModel.getSuggestions(it?.toString())
        }
    }

    private fun observeViewModel() {
        deliveryViewModel.suggestions.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            adapter.submitList(it.also { Log.i("test", "$it") })
        }
    }

    companion object {
        private const val ITEM_DECORATION_SPACING = 16f
    }
}

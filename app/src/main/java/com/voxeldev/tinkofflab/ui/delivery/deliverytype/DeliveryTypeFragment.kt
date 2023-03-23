package com.voxeldev.tinkofflab.ui.delivery.deliverytype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentDeliveryTypeBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.utils.SpaceItemDecoration

class DeliveryTypeFragment : Fragment(R.layout.fragment_delivery_type) {

    private lateinit var binding: FragmentDeliveryTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo: move to data layer
        val deliveryTypes = listOf(
            DeliveryTypeModel(
                R.string.delivery_type_courier,
                R.drawable.ic_delivery
            ) {
                App.router.navigateTo(Screens.AddressAutofill())
            },
            // todo: remove pickup
            DeliveryTypeModel(
                R.string.delivery_type_pickup,
                R.drawable.ic_pickup
            ) {
                // todo: move to address input screen
            }
        )
        binding.rvDeliveryType.addItemDecoration(
            SpaceItemDecoration(requireContext(), ITEM_DECORATION_SPACING)
        )
        binding.rvDeliveryType.adapter = DeliveryTypeAdapter().apply {
            submitList(deliveryTypes)
        }
    }

    companion object {
        private const val ITEM_DECORATION_SPACING = 16f
    }
}

package com.voxeldev.tinkofflab.ui.delivery.deliverytype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentDeliveryTypeBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.utils.SpaceItemDecoration

class DeliveryTypeFragment : BaseFragment<FragmentDeliveryTypeBinding>() {

    private val deliveryTypes by lazy {
        listOf(
            DeliveryTypeModel(
                R.string.delivery_type_courier,
                R.drawable.ic_delivery
            ) {
                App.router.navigateTo(Screens.Onboarding())
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryTypeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            rvDeliveryType.addItemDecoration(
                SpaceItemDecoration(requireContext(), ITEM_DECORATION_SPACING)
            )
            rvDeliveryType.adapter = DeliveryTypeAdapter().apply {
                submitList(deliveryTypes)
            }
        }

        addEndIconMenu()
    }

    companion object {

        private const val ITEM_DECORATION_SPACING = 16f
    }
}

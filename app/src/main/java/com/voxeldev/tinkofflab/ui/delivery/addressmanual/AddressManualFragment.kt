package com.voxeldev.tinkofflab.ui.delivery.addressmanual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.databinding.FragmentAddressManualBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
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
                val data = listOf(
                    edittextIndex.text,
                    edittextCountry.text,
                    edittextRegion.text,
                    edittextCity.text,
                    edittextStreet.text,
                    edittextHouse.text
                )

                sharedOrderViewModel.setAddress(
                    ExpressAddressModel(
                        data.filterNot { it.isNullOrEmpty() }.joinToString(),
                        // todo: fix
                        0f,
                        0f
                    )
                )

                App.router.navigateTo(Screens.Appointment())
            }
        }
    }
}

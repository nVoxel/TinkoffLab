package com.voxeldev.tinkofflab.ui.delivery.orderplaced

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentOrderPlacedBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel

class OrderPlacedFragment : BaseFragment<FragmentOrderPlacedBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderPlacedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            sharedOrderViewModel.createdOrder?.let {
                textviewWhere.text = it.address.address
                textviewWhen.text = it.deliverySlot.toString(resources)
            }
            buttonContinue.setOnClickListener {
                App.router.newRootScreen(Screens.HostFragment(R.id.item_orders))
            }
        }
    }
}

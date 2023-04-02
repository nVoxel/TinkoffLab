package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.voxeldev.tinkofflab.databinding.FragmentAppointmentBinding
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.DeliveryViewModel
import com.voxeldev.tinkofflab.ui.delivery.onboarding.AppointmentViewModel

class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    private val deliveryViewModel: DeliveryViewModel by activityViewModels()
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)

        return binding?.root
    }
}

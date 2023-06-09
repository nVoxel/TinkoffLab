package com.voxeldev.tinkofflab.ui.delivery.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.databinding.FragmentOnboardingBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private val viewModel: SharedOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonContinue?.setOnClickListener {
            viewModel.orderEditModeEnabled = false
            App.router.navigateTo(Screens.Appointment())
        }
        addEndIconMenu()
    }
}

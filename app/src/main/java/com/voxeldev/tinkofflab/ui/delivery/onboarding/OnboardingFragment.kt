package com.voxeldev.tinkofflab.ui.delivery.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voxeldev.tinkofflab.databinding.FragmentOnboardingBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

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

        binding?.run {
            buttonContinue.setOnClickListener {
                App.router.navigateTo(Screens.AddressAutofill())
            }
        }
    }
}

package com.voxeldev.tinkofflab.ui.toggleaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.data.local.AddressInputModeRepositoryImpl.Companion.toAddressInputMode
import com.voxeldev.tinkofflab.data.local.AddressInputModeRepositoryImpl.Companion.toBoolean
import com.voxeldev.tinkofflab.databinding.FragmentToggleAddressBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.MainActivity
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel

class ToggleAddressFragment : BaseFragment<FragmentToggleAddressBinding>() {

    private val viewModel: SharedOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToggleAddressBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        binding?.addressSwitch?.isChecked = viewModel.addressInputMode?.toBoolean() ?: false
        (activity as? MainActivity)?.hideMenuItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            buttonContinue.setOnClickListener {
                onSave(addressSwitch.isChecked)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.showMenuItems()
    }

    private fun onSave(isChecked: Boolean) {
        val newMode = isChecked.toAddressInputMode()
        val oldMode = viewModel.addressInputMode
        viewModel.setAddressInputMode(newMode)
        if (newMode != oldMode)
            App.router.backTo(Screens.Onboarding())
        else
            App.router.exit()
    }
}

package com.voxeldev.tinkofflab.ui.toggleaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.voxeldev.tinkofflab.databinding.FragmentToggleAddressBinding
import com.voxeldev.tinkofflab.ui.base.BaseFragment

class ToggleAddressFragment : BaseFragment<FragmentToggleAddressBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToggleAddressBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            buttonContinue.setOnClickListener {
                setFragmentResult(
                    TOGGLE_REQUEST_KEY,
                    bundleOf(TOGGLE_KEY to addressSwitch.isChecked)
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    companion object {

        const val TOGGLE_REQUEST_KEY = "TOGGLE_REQUEST_KEY"
        const val TOGGLE_KEY = "TOGGLE_KEY"
    }
}

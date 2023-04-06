package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAppointmentBinding
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.scrollToBottom
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)

        setupDaysChipGroup()

        with(binding!!) {
            savedInstanceState?.let {
                appointmentViewModel.checkedChipIndex?.let { index ->
                    chipgroupDays.check(chipgroupDays.getChildAt(index).id)
                }
            }

            edittextAddress.apply {
                setText(sharedOrderViewModel.sharedAddress?.address ?: "")
                setOnClickListener { App.router.backTo(Screens.AddressAutofill()) }
            }

            chipgroupDays.setOnCheckedStateChangeListener { group, checkedId ->
                if (checkedId.isNotEmpty()) chipCheckedCallback(group, checkedId[0])
            }

            textinputedittextComment.apply {
                appointmentViewModel.commentTextCallback( // init counter text
                    this.text?.toString() ?: "", resources
                )

                addTextChangedListener(
                    onTextChanged = { text, _, _, _ ->
                        text?.let {
                            appointmentViewModel.commentTextCallback(it.toString(), resources)
                        }
                        scrollviewRoot.scrollToBottom() // scroll to the bottom, so the counter is visible
                    }
                )
            }
            buttonContinue.setOnClickListener {
                val selectedTimeSlot =
                    appointmentViewModel.selectedTimeSlot ?: return@setOnClickListener

                sharedOrderViewModel.setDeliverySlot(selectedTimeSlot)
                sharedOrderViewModel.setComment(
                    binding?.textinputedittextComment?.text?.toString() ?: ""
                )

                // TODO: Navigate to confirmation screen
            }
        }

        with(appointmentViewModel) {
            observe(slotsAdapter, ::handleSlotsAdapter)
            observe(isLoading, ::toggleLoading)
            observe(continueButtonText, ::handleContinueButtonText)
            observe(commentHintText, ::handleHintText)
            observe(commentCounterText, ::handleCounterText)
            observe(exception, ::handleException)
        }

        return binding?.root
    }

    private fun handleSlotsAdapter(adapter: AppointmentTimeslotsAdapter?) {
        adapter?.let { binding?.recyclerviewTimeslots?.adapter = adapter }
        appointmentViewModel.getContinueButtonText(resources)
    }

    private fun setupDaysChipGroup() {
        fun createChip(text: String) {
            (layoutInflater.inflate(
                R.layout.item_day_chip, binding?.chipgroupDays, false
            ) as Chip).apply {
                this.text = text
                binding?.chipgroupDays?.addView(this)
            }
        }

        appointmentViewModel.chipDays.forEach {
            createChip(it.toRelativeString(LocalDate.now(), resources))
        }
    }

    private fun chipCheckedCallback(group: ChipGroup, checkedId: Int) {
        val chip = group.findViewById<Chip>(checkedId)
        val index = group.indexOfChild(chip)

        appointmentViewModel.getSlotsAdapter(index)
    }

    private fun handleContinueButtonText(text: String?) =
        text?.let {
            toggleErrorState(false)
            binding?.buttonContinue?.setText(text)
        } ?: run {
            toggleErrorState()
        }

    private fun handleHintText(text: String?) =
        text?.let { binding?.textinputlayoutComment?.hint = it }

    private fun handleCounterText(text: String?) =
        text?.let { binding?.textviewCounter?.text = it }

    private fun toggleLoading(isLoading: Boolean?) =
        isLoading?.let { binding?.blockingLoader?.layoutLoader?.isVisible = it }

    private fun toggleErrorState(isError: Boolean = true) {
        toggleLoading(false)
        binding?.buttonContinue?.isVisible = !isError
    }

    private fun handleException(exception: Exception?) {
        showSnackbar(R.string.request_process_error)
        Log.e(LOG_TAG, exception?.stackTraceToString() ?: "Unknown error")

        toggleErrorState()
    }

    companion object {
        private const val LOG_TAG = "AppointmentFragment"
    }
}

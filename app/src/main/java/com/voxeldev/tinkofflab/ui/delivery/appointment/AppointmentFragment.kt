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
import com.voxeldev.tinkofflab.domain.models.config.AddressInputMode
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import com.voxeldev.tinkofflab.ui.utils.scrollToBottom
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    private var shouldReloadDaysChipGroup = false // used to fix navigation-related bug

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)

        if (!sharedOrderViewModel.orderEditModeEnabled) setupDaysChipGroup()

        with(binding!!) {
            savedInstanceState?.let { reloadDaysChipGroup() }

            binding?.edittextAddress?.setOnClickListener {
                if (sharedOrderViewModel.orderEditModeEnabled)
                    App.router.navigateTo(
                        when (sharedOrderViewModel.addressInputMode) {
                            AddressInputMode.AUTOFILL -> Screens.AddressAutofill()
                            AddressInputMode.MANUAL -> Screens.AddressManual()
                            null -> Screens.Onboarding()
                        }
                    )
                else App.router.exit()
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

            buttonContinue.setOnClickListener { continueButtonOnClick() }
        }

        with(appointmentViewModel) {
            observe(slotsAdapter, ::handleSlotsAdapter)
            observe(isLoading, ::toggleLoading)
            observe(continueButtonText, ::handleContinueButtonText)
            observe(commentHintText, ::handleHintText)
            observe(commentCounterText, ::handleCounterText)
            observe(exception, ::handleException)
            observe(orderUpdateSuccess) {
                App.router.newRootScreen(Screens.HostFragment(R.id.item_orders))
            }
        }


        with(sharedOrderViewModel) {
            observe(sharedAddress, ::handleAddress)
            observe(deliverySlot, ::handleDeliverySlot)
            observe(comment, ::handleComment)
        }

        return binding?.root
    }

    override fun onPause() {
        super.onPause()
        shouldReloadDaysChipGroup = true
    }

    override fun onResume() {
        super.onResume()
        if (shouldReloadDaysChipGroup) {
            reloadDaysChipGroup()
            shouldReloadDaysChipGroup = false
        }
    }

    private fun handleAddress(address: ExpressAddressModel?) {
        binding?.edittextAddress?.setText(address?.address ?: "")
    }

    private fun handleDeliverySlot(deliverySlot: TimeSlotModel?) {
        appointmentViewModel.run {
            editTimeSlot = deliverySlot
            if (checkedChipIndex != null) return
            setupDaysChipGroup()
            binding?.run {
                val foundCheckedChipIndex = findCheckedChipIndex()
                checkedChipIndex = foundCheckedChipIndex
                chipgroupDays.getChildAt(foundCheckedChipIndex)?.let { chipgroupDays.check(it.id) }
            }
        }
    }

    private fun handleComment(comment: String?) =
        binding?.textinputedittextComment?.setText(comment ?: "")

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

    private fun reloadDaysChipGroup() {
        binding?.run {
            setupDaysChipGroup()
            appointmentViewModel.checkedChipIndex?.let { index ->
                chipgroupDays.check(chipgroupDays.getChildAt(index).id)
            }
        }
    }

    private fun chipCheckedCallback(group: ChipGroup, checkedId: Int) {
        val chip = group.findViewById<Chip>(checkedId)
        val index = group.indexOfChild(chip)

        appointmentViewModel.getSlotsAdapter(index, shouldReloadDaysChipGroup)
    }

    private fun continueButtonOnClick() {
        val selectedTimeSlot =
            appointmentViewModel.selectedTimeSlot ?: return

        sharedOrderViewModel.run {
            setDeliverySlot(selectedTimeSlot)
            setComment(
                binding?.textinputedittextComment?.text?.toString() ?: ""
            )

            if (!orderEditModeEnabled) return@run

            getOrder()?.let { appointmentViewModel.updateOrder(it) }
            return
        }

        App.router.navigateTo(Screens.Confirmation())
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

package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentAppointmentBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.DeliveryViewModel
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AppointmentFragment : BaseFragment<FragmentAppointmentBinding>() {

    private val deliveryViewModel: DeliveryViewModel by activityViewModels()
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    private val chipDays: List<LocalDate> by lazy { appointmentViewModel.getDays() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)

        setupDaysChipGroup()

        if (savedInstanceState != null) {
            with(binding!!) {
                appointmentViewModel.getCheckedChipIndex()?.let { index ->
                    chipgroupDays.check(chipgroupDays.getChildAt(index).id)
                }
            }
        }

        with(binding!!) {
            edittextAddress.setOnClickListener {
                App.router.backTo(Screens.AddressAutofill())
            }

            chipgroupDays.setOnCheckedStateChangeListener { group, checkedId ->
                if (checkedId.isEmpty()) return@setOnCheckedStateChangeListener
                chipCheckedCallback(group, checkedId[0])
            }

            buttonContinue.setOnClickListener {
                buttonContinueCallback()
            }
        }

        with(appointmentViewModel) {
            observe(addresses, ::handleAddresses)
            observe(slots, ::handleSlots)
            observe(exception, ::handleException)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appointmentViewModel.getAddresses()
    }

    private fun handleAddresses(addresses: List<AddressModel>?) {
        addresses?.let {
            if (it.isEmpty()) {
                toggleLoading(false)
                return
            }

            binding?.edittextAddress?.setText(it[0].address)

            toggleLoading(false)
        } ?: showSnackbar(R.string.request_process_error)
    }

    private fun handleSlots(slots: List<TimeSlotModel>?) {
        slots?.let {
            if (slots.isEmpty()) {
                toggleLoading(false)
                return
            }

            binding?.recyclerviewTimeslots?.adapter = AppointmentTimeslotsAdapter(slots) { slot, index ->
                appointmentViewModel.setSelectedTimeSlot(slot, index)
            }

            val selectedTimeSlotIndex = appointmentViewModel.getSelectedTimeSlotIndex() ?: 0
            appointmentViewModel.setSelectedTimeSlot(
                slots[selectedTimeSlotIndex], selectedTimeSlotIndex
            )

            if (appointmentViewModel.getSelectedTimeSlot() != null) {
                (binding?.recyclerviewTimeslots?.adapter as AppointmentTimeslotsAdapter).selectItem(
                    selectedTimeSlotIndex
                )
            }

            binding?.buttonContinue?.apply {
                visibility = View.VISIBLE
                setText(
                    resources.getString(
                        R.string.appointment_button_placeholder, LocalDate
                            .parse(slots[selectedTimeSlotIndex].date, DateTimeFormatter.ISO_DATE)
                            .toRelativeString(LocalDate.now(), resources)
                            .replaceFirstChar { it.lowercase() }
                    )
                )
            }

            toggleLoading(false)
        } ?: showSnackbar(R.string.request_process_error)
    }

    private fun setupDaysChipGroup() {
        fun createChip(text: String) {
            (layoutInflater.inflate(
                R.layout.item_day_chip,
                binding?.chipgroupDays,
                false
            ) as Chip).apply {
                this.text = text
                binding?.chipgroupDays?.addView(this)
            }
        }

        for (day in chipDays) {
            createChip(
                day.toRelativeString(
                    LocalDate.now(),
                    resources
                )
            )
        }
    }

    private fun chipCheckedCallback(group: ChipGroup, checkedId: Int) {
        val chip = group.findViewById<Chip>(checkedId)

        val index = group.indexOfChild(chip)
        appointmentViewModel.setCheckedChipIndex(index)

        // reset selected time slot on day change
        appointmentViewModel.setSelectedTimeSlot(null, null)

        toggleLoading(true)

        val day = chipDays[index]
        appointmentViewModel.getSlots(day.format(DateTimeFormatter.ISO_DATE))
    }

    private fun buttonContinueCallback() {
        val selectedTimeSlot = appointmentViewModel.getSelectedTimeSlot() ?: return

        showSnackbar(selectedTimeSlot.toString())

        deliveryViewModel.setSharedOrderDeliverySlot(selectedTimeSlot)

        // TODO: Navigate to confirmation screen
    }

    private fun toggleLoading(isLoading: Boolean) {
        with(binding!!) {
            blockingLoader.layoutLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) buttonContinue.visibility = View.GONE
        }
    }

    private fun handleException(exception: Exception?) {
        showSnackbar(R.string.request_process_error)
        Log.e(LOG_TAG, exception?.stackTraceToString() ?: "Unknown error")
    }

    companion object {
        private const val LOG_TAG = "AppointmentFragment"
    }
}

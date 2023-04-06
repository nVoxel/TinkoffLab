package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetSlotsUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.generateOrderedDates
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.exceptions.EmptyListException
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getSlotsUseCase: GetSlotsUseCase
) : BaseViewModel() {

    private val _slotsAdapter: MutableLiveData<AppointmentTimeslotsAdapter> = MutableLiveData()
    val slotsAdapter: LiveData<AppointmentTimeslotsAdapter> = _slotsAdapter

    private val _continueButtonText: MutableLiveData<String?> = MutableLiveData()
    val continueButtonText: LiveData<String?> = _continueButtonText

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _commentHintText: MutableLiveData<String> = MutableLiveData()
    val commentHintText: LiveData<String> = _commentHintText

    private val _commentCounterText: MutableLiveData<String> = MutableLiveData()
    val commentCounterText: LiveData<String> = _commentCounterText

    var selectedTimeSlot: TimeSlotModel? = null

    var selectedTimeSlotIndex: Int? = null

    val chipDays by lazy { LocalDate.now().generateOrderedDates(DAYS_COUNT) }

    var checkedChipIndex: Int? = null

    /**
     * @param index Selected chip index
     * @return Timeslots adapter
     */
    fun getSlotsAdapter(index: Int) {
        checkedChipIndex = index

        // reset selected time slot on day change
        selectedTimeSlot = null
        selectedTimeSlotIndex = null

        _isLoading.value = true

        getSlotsUseCase(
            chipDays[index].format(DateTimeFormatter.ISO_DATE),
            viewModelScope
        ) { either ->
            either.fold(::handleException) {
                if (it.isEmpty()) {
                    handleException(EmptyListException())
                    return@fold
                }

                if (selectedTimeSlotIndex == null) {
                    selectedTimeSlot = it.first()
                    selectedTimeSlotIndex = 0
                }

                _slotsAdapter.value = AppointmentTimeslotsAdapter(it) { slot, index ->
                    selectedTimeSlot = slot
                    selectedTimeSlotIndex = index
                }

                _slotsAdapter.value?.selectItem(selectedTimeSlotIndex!!)
            }
        }
    }

    fun getContinueButtonText(resources: Resources) {
        _continueButtonText.value = selectedTimeSlot?.let {
            resources.getString(
                R.string.appointment_button_placeholder, LocalDate
                    .parse(selectedTimeSlot?.date, DateTimeFormatter.ISO_DATE)
                    .toRelativeString(LocalDate.now(), resources)
                    .replaceFirstChar { it.lowercase() }
            )
        }
    }

    fun commentTextCallback(text: String, resources: Resources) {
        _commentHintText.value =
            if (text.isEmpty()) resources.getString(R.string.comment_hint_text) else ""

        val placeholder =
            if (text.isEmpty()) R.string.comment_empty_placeholder else R.string.comment_placeholder
        val length = if (text.isEmpty()) COMMENT_MAX_LENGTH else COMMENT_MAX_LENGTH - text.length

        _commentCounterText.value = resources.getString(
            placeholder,
            length,
            resources.getQuantityString(
                R.plurals.comment_symbols,
                length
            )
        )
    }

    companion object {
        private const val DAYS_COUNT = 15
        private const val COMMENT_MAX_LENGTH = 150
    }
}

package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetSlotsUseCase
import com.voxeldev.tinkofflab.domain.usecases.expressapi.UpdateOrderUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.SingleLiveEvent
import com.voxeldev.tinkofflab.ui.utils.generateOrderedDates
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import com.voxeldev.tinkofflab.utils.exceptions.EmptyListException
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getSlotsUseCase: GetSlotsUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
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

    val orderUpdateSuccess = SingleLiveEvent<Unit>()

    val chipDays by lazy {
        LocalDate.now().generateOrderedDates(DAYS_COUNT, editTimeSlotLocalDate)
    }

    var editTimeSlot: TimeSlotModel? = null
    private val editTimeSlotLocalDate by lazy {
        editTimeSlot?.date?.let {
            runCatching { LocalDate.parse(it) }
                .onSuccess { return@lazy it }
        }

        return@lazy null
    }

    var selectedTimeSlot: TimeSlotModel? = null

    var selectedTimeSlotIndex: Int? = null

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
                    if (editTimeSlot != null && checkedChipIndex == findCheckedChipIndex()) {
                        selectedTimeSlotIndex = findSelectedTimeSlotIndex(it)
                        selectedTimeSlot = it[selectedTimeSlotIndex!!]
                    } else {
                        selectedTimeSlotIndex = 0
                        selectedTimeSlot = it.first()
                    }
                }

                _slotsAdapter.value = AppointmentTimeslotsAdapter(it) { slot, index ->
                    selectedTimeSlot = slot
                    selectedTimeSlotIndex = index
                }


                _slotsAdapter.value?.selectItem(selectedTimeSlotIndex!!)
            }
        }
    }

    fun updateOrder(order: OrderModel) {
        _isLoading.value = true
        updateOrderUseCase(order, viewModelScope) {either ->
            either.fold(::handleException) {
                orderUpdateSuccess.value = Unit
            }
            _isLoading.value = false
        }
    }

    fun getContinueButtonText(resources: Resources) {
        _continueButtonText.value = selectedTimeSlot?.let {
            resources.getString(
                R.string.appointment_button_placeholder,
                LocalDate.parse(selectedTimeSlot?.date, DateTimeFormatter.ISO_DATE)
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

    fun findCheckedChipIndex(): Int =
        editTimeSlot?.let { _ ->
            chipDays.indexOfFirst { it == editTimeSlotLocalDate }
        } ?: DEFAULT_FIND_CHECKED_CHIP_INDEX_VALUE

    private fun findSelectedTimeSlotIndex(list: List<TimeSlotModel>): Int =
        editTimeSlot?.let { editTimeSlot ->
            list.indexOfFirst {
                it.timeFrom == editTimeSlot.timeFrom && it.timeTo == editTimeSlot.timeTo
            }
        } ?: DEFAULT_FIND_SELECTED_TIMESLOT_INDEX_VALUE

    companion object {
        private const val DAYS_COUNT = 15
        private const val COMMENT_MAX_LENGTH = 150

        private const val DEFAULT_FIND_CHECKED_CHIP_INDEX_VALUE = 0
        private const val DEFAULT_FIND_SELECTED_TIMESLOT_INDEX_VALUE = 0
    }
}

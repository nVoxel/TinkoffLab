package com.voxeldev.tinkofflab.ui.delivery.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetAddressesUseCase
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetSlotsUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.generateOrderedDates
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getSlotsUseCase: GetSlotsUseCase
) : BaseViewModel() {

    private val _addresses: MutableLiveData<List<AddressModel>> = MutableLiveData()
    val addresses: LiveData<List<AddressModel>> = _addresses

    private val _slots: MutableLiveData<List<TimeSlotModel>> = MutableLiveData()
    val slots: LiveData<List<TimeSlotModel>> = _slots

    private var selectedTimeSlot: TimeSlotModel? = null
    private var selectedTimeSlotIndex: Int? = null

    private var checkedChipIndex: Int? = null

    fun getAddresses() = getAddressesUseCase(BaseUseCase.None, viewModelScope) { either ->
        either.fold(::handleException) {
            _addresses.value = it
        }
    }

    fun getSlots(date: String) = getSlotsUseCase(date, viewModelScope) { either ->
        either.fold(::handleException) {
            _slots.value = it
        }
    }

    fun getDays(): List<LocalDate> = LocalDate.now().generateOrderedDates(DAYS_COUNT)

    fun getSelectedTimeSlot(): TimeSlotModel? = selectedTimeSlot
    fun getSelectedTimeSlotIndex(): Int? = selectedTimeSlotIndex
    fun setSelectedTimeSlot(timeSlot: TimeSlotModel?, index: Int?) {
        selectedTimeSlot = timeSlot
        selectedTimeSlotIndex = index
    }

    fun getCheckedChipIndex(): Int? = checkedChipIndex
    fun setCheckedChipIndex(chipId: Int) {
        checkedChipIndex = chipId
    }

    companion object {
        private const val DAYS_COUNT = 15
    }
}

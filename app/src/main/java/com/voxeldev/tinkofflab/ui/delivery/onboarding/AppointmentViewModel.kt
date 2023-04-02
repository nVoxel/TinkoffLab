package com.voxeldev.tinkofflab.ui.delivery.onboarding

import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetAddressesUseCase
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetSlotsUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getSlotsUseCase: GetSlotsUseCase
) : BaseViewModel() {

}

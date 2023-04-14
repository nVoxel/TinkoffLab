package com.voxeldev.tinkofflab.ui.delivery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.config.AddressInputMode
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.domain.usecases.config.GetAddressInputModeUseCase
import com.voxeldev.tinkofflab.domain.usecases.config.SetAddressInputModeUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedOrderViewModel @Inject constructor(
    private val getAddressInputModeUseCase: GetAddressInputModeUseCase,
    private val setAddressInputModeUseCase: SetAddressInputModeUseCase,
) : BaseViewModel() {

    private val orderBuilder = MutableLiveData(OrderModel.Builder())

    var sharedAddress: ExpressAddressModel? = null

    fun getOrder() = orderBuilder.value?.build()

    var addressInputMode: AddressInputMode? = null
        private set

    fun initAddressInputMode() {
        if (addressInputMode == null)
            getAddressInputModeUseCase(BaseUseCase.None, viewModelScope) { either ->
                either.fold(::handleException) {
                    addressInputMode = it
                }
            }
    }

    fun setAddressInputMode(mode: AddressInputMode) {
        setAddressInputModeUseCase(mode, viewModelScope) { either ->
            either.fold(::handleException) {
                addressInputMode = mode
            }
        }
    }

    fun setAddress(
        address: ExpressAddressModel
    ) {
        sharedAddress = address
        orderBuilder.value?.address(address)
    }

    fun setPaymentMethod(paymentMethod: String) =
        orderBuilder.value?.paymentMethod(paymentMethod)

    fun setDeliverySlot(deliverySlot: TimeSlotModel) =
        orderBuilder.value?.deliverySlot(deliverySlot)

    fun setItems(items: List<ItemModel>) = orderBuilder.value?.items(items)

    fun setComment(comment: String) = orderBuilder.value?.comment(comment)

    fun setStatus(status: String) = orderBuilder.value?.status(status)
}

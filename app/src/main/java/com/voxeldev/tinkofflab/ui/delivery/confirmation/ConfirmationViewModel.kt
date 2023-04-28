package com.voxeldev.tinkofflab.ui.delivery.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.usecases.expressapi.CreateOrderUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
) : BaseViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    val createdOrder = SingleLiveEvent<OrderModel>()

    fun createOrder(order: OrderModel) {
        _loading.value = true
        createOrderUseCase(order, viewModelScope) { either ->
            either.fold(::handleException) {
                createdOrder.value = it
            }
            _loading.value = false
        }
    }
}

package com.voxeldev.tinkofflab.ui.delivery.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.usecases.expressapi.CreateOrderUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
) : BaseViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    val orderCreationSuccess = SingleLiveEvent<Unit>()

    fun createOrder() {
        viewModelScope.launch {
            _loading.value = true
            delay(FAKE_LOADING_DELAY) // request
            _loading.value = false
            orderCreationSuccess.value = Unit
        }
    }

    // todo: fix api (500 error)
    fun createOrder(order: OrderModel) {
        createOrderUseCase(order, viewModelScope) { either ->
            either.fold(::handleException) {
                orderCreationSuccess.value = Unit
            }
            _loading.value = false
        }
    }
}

private const val FAKE_LOADING_DELAY = 500L

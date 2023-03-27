package com.voxeldev.tinkofflab.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.domain.usecases.expressapi.GetOrdersUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : BaseViewModel() {

    private val _orders: MutableLiveData<List<OrderModel>> = MutableLiveData()
    val orders: LiveData<List<OrderModel>> = _orders

    fun getOrders() = getOrdersUseCase(BaseUseCase.None, viewModelScope) { either ->
        either.fold(::handleException) {
            _orders.value = it
        }
    }
}

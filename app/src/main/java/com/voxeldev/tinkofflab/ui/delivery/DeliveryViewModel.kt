package com.voxeldev.tinkofflab.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel

class DeliveryViewModel : BaseViewModel() {

    private val _sharedOrderBuilder = MutableLiveData(OrderModel.Builder())
    val sharedOrderBuilder: LiveData<OrderModel.Builder>
        get() = _sharedOrderBuilder

    fun getSharedOrder() = _sharedOrderBuilder.value?.build()

    fun setSharedOrderAddress(
        address: ExpressAddressModel
    ) = _sharedOrderBuilder.value?.address(address)

    fun setSharedOrderPaymentMethod(paymentMethod: String) =
        _sharedOrderBuilder.value?.paymentMethod(paymentMethod)

    fun setSharedOrderDeliverySlot(deliverySlot: TimeSlotModel) =
        _sharedOrderBuilder.value?.deliverySlot(deliverySlot)

    fun setSharedOrderItems(items: List<ItemModel>) = _sharedOrderBuilder.value?.items(items)

    fun setSharedOrderComment(comment: String) = _sharedOrderBuilder.value?.comment(comment)

    fun setSharedOrderStatus(status: String) = _sharedOrderBuilder.value?.status(status)
}

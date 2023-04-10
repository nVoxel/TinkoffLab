package com.voxeldev.tinkofflab.ui.delivery

import androidx.lifecycle.MutableLiveData
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel

class SharedOrderViewModel : BaseViewModel() {

    private val orderBuilder = MutableLiveData(OrderModel.Builder())

    var sharedAddress: ExpressAddressModel? = null

    fun getOrder() = orderBuilder.value?.build()

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

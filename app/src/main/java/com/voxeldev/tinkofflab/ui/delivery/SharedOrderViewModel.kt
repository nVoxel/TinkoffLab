package com.voxeldev.tinkofflab.ui.delivery

import androidx.lifecycle.LiveData
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
import com.voxeldev.tinkofflab.ui.cart.CartItemModel
import com.voxeldev.tinkofflab.ui.delivery.confirmation.paymentmethod.PaymentMethod
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedOrderViewModel @Inject constructor(
    private val getAddressInputModeUseCase: GetAddressInputModeUseCase,
    private val setAddressInputModeUseCase: SetAddressInputModeUseCase,
) : BaseViewModel() {

    private val orderBuilder = MutableLiveData(OrderModel.Builder())

    private val _cartItems = MutableLiveData<List<CartItemModel>?>()
    val cartItems: LiveData<List<CartItemModel>?>
        get() = _cartItems

    private val _sharedAddress = MutableLiveData<ExpressAddressModel?>(null)
    val sharedAddress: LiveData<ExpressAddressModel?>
        get() = _sharedAddress

    private val _paymentMethod = MutableLiveData(PaymentMethod.CARD)
    val paymentMethod: LiveData<PaymentMethod>
        get() = _paymentMethod

    private val _deliverySlot = MutableLiveData<TimeSlotModel>()
    val deliverySlot: LiveData<TimeSlotModel>
        get() = _deliverySlot

    private val _comment = MutableLiveData<String>()
    val comment: LiveData<String>
        get() = _comment

    var createdOrder: OrderModel? = null

    fun getOrder() = orderBuilder.value?.build()

    var addressInputMode: AddressInputMode? = null
        private set

    var orderEditModeEnabled = false
        set(value) {
            if (field != value && field)
                resetAddress()
            field = value
        }

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

    fun setOrder(orderModel: OrderModel) {
        orderModel.run {
            setAddress(address)
            runCatching { setPaymentMethod(PaymentMethod.valueOf(paymentMethod)) }
            setDeliverySlot(deliverySlot)
            setItems(items)
            setComment(comment)
            setStatus(status)
            setId(id)
        }
    }

    fun setAddress(
        address: ExpressAddressModel
    ) {
        _sharedAddress.value = address
        orderBuilder.value?.address(address)
    }

    fun resetAddress() {
        _sharedAddress.value = null
    }

    fun setPaymentMethod(paymentMethod: PaymentMethod) {
        _paymentMethod.value = paymentMethod
        orderBuilder.value?.paymentMethod(paymentMethod.toString())
    }

    fun setDeliverySlot(deliverySlot: TimeSlotModel) {
        _deliverySlot.value = deliverySlot
        orderBuilder.value?.deliverySlot(deliverySlot)
    }

    fun setItems(items: List<ItemModel>) = orderBuilder.value?.items(items)

    fun setItems(items: List<CartItemModel>) {
        _cartItems.value?.let {
            // Fix catalog disappearing items
            items.forEach { newItem ->
                it.find { existingItem -> existingItem.itemModel == newItem.itemModel }
                    ?.count = newItem.count
            }
        } ?: run { _cartItems.value = items }

        _cartItems.value = _cartItems.value

        setItems(items.map { ItemModel(it.itemModel.name, it.itemModel.price) })
    }

    fun clearItems() {
        _cartItems.value = null
        setItems(listOf<ItemModel>())
    }

    fun setComment(comment: String) {
        _comment.value = comment
        orderBuilder.value?.comment(comment)
    }

    fun setStatus(status: String) = orderBuilder.value?.status(status)

    fun setId(id: Int) = orderBuilder.value?.id(id)
}

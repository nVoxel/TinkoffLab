package com.voxeldev.tinkofflab.ui.delivery

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.usecases.dadataapi.GetAddressSuggestionsUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.ExpressAddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val getAddressSuggestionsUseCase: GetAddressSuggestionsUseCase
) : BaseViewModel() {

    private val suggestionsFlow = MutableStateFlow<String?>(null)

    private val _sharedOrderBuilder = MutableLiveData(OrderModel.Builder())
    val sharedOrderBuilder: LiveData<OrderModel.Builder>
        get() = _sharedOrderBuilder

    private val _suggestions = MutableLiveData<List<AddressModel>>()
    val suggestions: LiveData<List<AddressModel>>
        get() = _suggestions

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val locale get() = Resources.getSystem().configuration.locales[0].language

    init {
        subscribeToChanges()
    }

    private fun subscribeToChanges() {
        suggestionsFlow
            .filterNot { it.isNullOrBlank() }
            .map { it?.trim() }
            .distinctUntilChanged()
            .onEach { _loading.postValue(true) }
            .debounce(SEARCH_TIMEOUT)
            .onEach { query ->
                getAddressSuggestionsUseCase(query!! to locale, viewModelScope) { either ->
                    either.fold(::handleException) {
                        _suggestions.postValue(it)
                    }
                }
                _loading.postValue(false)
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun getSuggestions(query: String?) {
        suggestionsFlow.value = query
    }

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

    companion object {

        private const val SEARCH_TIMEOUT = 1000L // millis
    }
}

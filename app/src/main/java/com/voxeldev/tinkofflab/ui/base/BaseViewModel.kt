package com.voxeldev.tinkofflab.ui.base

import androidx.lifecycle.ViewModel
import com.voxeldev.tinkofflab.ui.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    private val _exception: SingleLiveEvent<Exception> = SingleLiveEvent()
    val exception: SingleLiveEvent<Exception> = _exception

    protected fun handleException(exception: Exception) {
        _exception.postValue(exception)
    }
}

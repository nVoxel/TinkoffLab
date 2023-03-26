package com.voxeldev.tinkofflab.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception

    protected fun handleException(exception: Exception) {
        _exception.value = exception
    }
}

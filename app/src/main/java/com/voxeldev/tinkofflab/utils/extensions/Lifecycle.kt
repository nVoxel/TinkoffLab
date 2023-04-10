package com.voxeldev.tinkofflab.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Exception>>
        LifecycleOwner.exception(liveData: L, body: (Exception?) -> Unit) =
    liveData.observe(this, Observer(body))

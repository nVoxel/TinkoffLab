package com.voxeldev.tinkofflab.data.local.datasource

import com.voxeldev.tinkofflab.utils.functional.Either

interface SharedPrefsApi<T> {

    fun getValue(): Either<Exception, T>

    fun setValue(value: T): Either<Exception, Unit>
}

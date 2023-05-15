package com.voxeldev.tinkofflab.data.local.datasource

import com.voxeldev.tinkofflab.utils.functional.Either

interface ReadOnlyApi<T> {

    fun getValue(): Either<Exception, T>
}

package com.voxeldev.tinkofflab.domain.usecases.base

import com.voxeldev.tinkofflab.utils.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract suspend fun run(params: Params): Either<Exception, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Either<Exception, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}

package com.voxeldev.tinkofflab.domain.usecases.base

import com.voxeldev.tinkofflab.utils.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class BaseUseCase<in Params, out Type> constructor(
    private val asyncDispatcher: CoroutineDispatcher = Dispatchers.IO
) where Type : Any {

    abstract fun run(params: Params): Either<Exception, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        asyncDispatcher: CoroutineDispatcher = this.asyncDispatcher,
        onResult: suspend (Either<Exception, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(asyncDispatcher) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    object None
}

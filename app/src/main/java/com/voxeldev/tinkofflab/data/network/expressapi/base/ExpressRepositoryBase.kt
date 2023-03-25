package com.voxeldev.tinkofflab.data.network.expressapi.base

import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.utils.exceptions.NetworkIsNotAvailableException
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import retrofit2.Call
import retrofit2.HttpException

open class ExpressRepositoryBase(
    private val networkHandler: NetworkHandler
) {

    protected fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R
    ): Either<Exception, R> {
        if (!networkHandler.isNetworkAvailable())
            return Either.Left(NetworkIsNotAvailableException())

        return try {
            val response = call.execute()

            if (!response.isSuccessful)
                return Either.Left(HttpException(response))

            if (response.body() == null) {
                return Either.Left(
                    NullPointerException()
                )
            }

            Either.Right(transform(response.body()!!))
        } catch (exception: Exception) {
            Either.Left(exception)
        }
    }
}

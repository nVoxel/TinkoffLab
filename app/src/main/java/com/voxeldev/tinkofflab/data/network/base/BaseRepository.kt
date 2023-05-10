package com.voxeldev.tinkofflab.data.network.base

import com.voxeldev.tinkofflab.utils.exceptions.NetworkIsNotAvailableException
import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

@Suppress("TooGenericExceptionCaught", "ReturnCount")
open class BaseRepository(
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

            // if server error, then try again
            if (response.code().isServerError())
                processResponse(call.execute(), transform)
            else
                processResponse(response, transform)
        } catch (exception: Exception) {
            Either.Left(exception)
        }
    }

    private fun <T, R> processResponse(
        response: Response<T>,
        transform: (T) -> R
    ): Either<Exception, R> {
        if (!response.isSuccessful)
            return Either.Left(HttpException(response))

        if (response.body() == null) {
            return Either.Left(
                NullPointerException("Response body is null")
            )
        }

        return Either.Right(transform(response.body()!!))
    }

    companion object {

        private const val SERVER_ERROR_CLASS = 5
        private const val TO_RESPONSE_CLASS = 100

        private fun Int.isServerError() = this / TO_RESPONSE_CLASS == SERVER_ERROR_CLASS
    }
}

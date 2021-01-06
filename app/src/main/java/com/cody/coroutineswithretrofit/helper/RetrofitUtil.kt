package com.cody.coroutineswithretrofit.helper

import com.cody.coroutineswithretrofit.api.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> apiCall(dispatcher: CoroutineDispatcher, call: suspend () -> T): ApiResult<T> {
    return withContext(dispatcher) {
        try {
            ApiResult.ResponseSuccess(call.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ApiResult.NetworkFailure(throwable.message ?: "Unknown error.")
                is HttpException -> {
                    val code = throwable.code()
                    val error = throwable.message ?: "Unknown error."
                    ApiResult.ResponseFailure(code, error)
                }
                else -> ApiResult.ResponseFailure(504, throwable.message ?: "Unknown error.")
            }
        }
    }
}
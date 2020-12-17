package com.cody.coroutineswithretrofit.api

sealed class ApiResult<out T> {
    class ResponseSuccess<out T>(val value: T): ApiResult<T>()
    class ResponseFailure(val code: Int, val error: String): ApiResult<Nothing>()
    class NetworkFailure(val error: String): ApiResult<Nothing>()
}
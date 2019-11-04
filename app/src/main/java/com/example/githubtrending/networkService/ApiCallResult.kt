package com.example.githubtrending.networkService

import java.lang.Exception

sealed class ApiCallResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiCallResult<T>()
    data class Failure(val exception: Exception) : ApiCallResult<Nothing>()
}
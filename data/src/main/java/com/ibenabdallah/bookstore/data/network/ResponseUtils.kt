package com.ibenabdallah.bookstore.data.network

import retrofit2.Response


private inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

private inline fun <T : Any> Response<T>.onFailure(action: (Throwable) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(Throwable(message())) }
}

internal fun <I : Any, O : Any> Response<I>.toStatus(transform: (I) -> Status<O>): Status<O> {
    try {
        onSuccess { return transform(it) }
        onFailure { return Status.Error(it) }
        return Status.Error()
    } catch (e: Exception) {
        return Status.Error(e)
    }
}

sealed class Status<T> {
    data class Success<T>(val data: T) : Status<T>()
    data class Error<T>(val error: Throwable = Throwable()) : Status<T>()
}
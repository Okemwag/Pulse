// ===========================================
// core/common/src/main/java/com/okemwag/pulse/core/common/result/Result.kt
// ===========================================
package com.okemwag.pulse.core.common.result

/**
 * A generic wrapper for handling success/error states
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    inline fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Loading -> this
        }
    }

    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) {
            action(data)
        }
        return this
    }

    inline fun onError(action: (Throwable) -> Unit): Result<T> {
        if (this is Error) {
            action(exception)
        }
        return this
    }

    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}

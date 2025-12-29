package com.okemwag.common.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction for coroutine dispatchers to enable testing
 */
data class AppCoroutineDispatchers(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher
)
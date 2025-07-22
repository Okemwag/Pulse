package com.okemwag.pulse


import com.okemwag.pulse.core.blockchain.Web3Manager
import com.okemwag.pulse.core.common.utils.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializer @Inject constructor(
    private val web3Manager: Web3Manager,
    private val dispatchers: AppCoroutineDispatchers
) {

    private val applicationScope = CoroutineScope(SupervisorJob() + dispatchers.io)

    fun initialize() {
        initializeBlockchainConnection()
        // Add other initialization tasks here
    }

    private fun initializeBlockchainConnection() {
        applicationScope.launch {
            try {
                web3Manager.initialize()
                Timber.d("Blockchain connection initialized")
            } catch (e: Exception) {
                Timber.e(e, "Failed to initialize blockchain connection")
            }
        }
    }
}

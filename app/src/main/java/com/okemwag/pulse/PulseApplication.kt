package com.okemwag.pulse

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PulseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize logging
        initializeLogging()

        Timber.d("Pulse application started")
    }

    private fun initializeLogging() {
        // Always use DebugTree for now - can be configured later
        Timber.plant(Timber.DebugTree())
    }
}

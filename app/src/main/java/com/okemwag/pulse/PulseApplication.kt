package com.okemwag.pulse

import android.app.Application
import android.os.StrictMode
import timber.log.Timber

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

package com.okemwag.pulse
import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        // In production, send to crash reporting service
        // FakeCrashLibrary.log(priority, tag, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                // FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                // FakeCrashLibrary.logWarning(t)
            }
        }
    }
}

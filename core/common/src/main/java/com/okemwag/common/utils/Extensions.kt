package com.okemwag.common.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.okemwag.common.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Context Extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// String Extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.truncate(maxLength: Int): String {
    return if (length <= maxLength) this else "${take(maxLength)}..."
}

// Date Extensions
fun Date.toFormattedString(pattern: String = "MMM dd, yyyy"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Long.toDate(): Date = Date(this)

// Flow Extensions
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) }
        .catch { emit(Result.Error(it)) }
}

// Compose Extensions
@Composable
fun Int.toDp(): Dp {
    val density = LocalDensity.current
    return with(density) { this@toDp.toDp() }
}

// Logging Extensions
inline fun <reified T> T.logDebug(message: String) {
    Timber.tag(T::class.java.simpleName).d(message)
}

inline fun <reified T> T.logError(message: String, throwable: Throwable? = null) {
    Timber.tag(T::class.java.simpleName).e(throwable, message)
}
package com.app.expensetracker.core.utils

import android.util.Log
import com.app.expensetracker.BuildConfig

/**
 * App-wide logger that is a no-op in release builds.
 *
 * Usage:
 *   AppLogger.d("NavGraph", "Selected month: $month")
 *   AppLogger.e("Repository", "Firestore error", exception)
 *
 * In release builds (BuildConfig.DEBUG == false) every function
 * returns immediately without calling android.util.Log — so no
 * sensitive data leaks and no performance cost from string building.
 *
 * How to use the tag helper:
 *   companion object { private val TAG = AppLogger.tag(this) }
 *   AppLogger.d(TAG, "message")
 *
 * This gives you the class name automatically, e.g. "DashboardViewModel".
 */
object AppLogger {

    /** Debug — verbose dev info, stripped entirely in release */
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.d(tag, message)
    }

    /** Info — notable lifecycle events */
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.i(tag, message)
    }

    /** Warning — unexpected but recoverable */
    fun w(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) Log.w(tag, message, throwable)
            else Log.w(tag, message)
        }
    }

    /** Error — always log in debug; in release, send to crash reporter */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) Log.e(tag, message, throwable)
            else Log.e(tag, message)
        } else {
            // TODO: wire up Firebase Crashlytics here when you add it
            // FirebaseCrashlytics.getInstance().recordException(throwable ?: Exception(message))
        }
    }

    /**
     * Generates a consistent tag from any companion object or class.
     *
     * Usage inside a class:
     *   companion object { private val TAG = AppLogger.tag(this) }
     *
     * Result: tag = "DashboardViewModel" (stripped of $Companion suffix)
     */
    fun tag(obj: Any): String =
        obj::class.java.simpleName
            .removeSuffix("\$Companion")
            .take(23)   // Android log tag limit is 23 chars
}

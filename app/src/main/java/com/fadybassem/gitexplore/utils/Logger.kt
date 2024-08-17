package com.fadybassem.gitexplore.utils

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import java.util.Locale

object Logger {
    private const val NO_APP_CONTEXT_LOG_MESSAGE =
        "Could not log message because no application context is set to get strings from."

    /**
     * The [Context] to retrieve resources with.
     */
    private var sAppContext: Context? = null

    /**
     * The log tag.
     */
    private var sLogTag = Logger::class.java.simpleName

    /**
     * The log level.
     */
    private var sLogLevel = Log.VERBOSE

    /**
     * Sets the [Context] to be used to retrieve resources with.
     *
     * @param appContext The [Context] to be used to retrieve resources with.
     * @param logTag     The tag to log entries with.
     * @param logLevel   The level to filter the log entries with.
     */
    fun init(appContext: Context, logTag: String, logLevel: Int) {
        // NOTE ensure it's really the app context
        sAppContext = appContext.applicationContext
        sLogTag = logTag
        sLogLevel = logLevel
    }

    /**
     * Checks whether [.sAppContext] to be used to retrieve resources with is set. If not, logs the [.NO_APP_CONTEXT_LOG_MESSAGE] error message.
     *
     * @return True if [.sAppContext] to be used to retrieve resources with is set, false otherwise.
     */
    private fun checkAppContextSet(): Boolean {
        val appContextAvailable = sAppContext != null
        if (!appContextAvailable) {
            Log.e(sLogTag, NO_APP_CONTEXT_LOG_MESSAGE)
        }
        return appContextAvailable
    }

    /**
     * Does a verbose log.
     *
     * @param msg The log message.
     */
    fun verbose(msg: String?) {
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, msg!!)
        }
    }

    /**
     * Does a verbose log.
     *
     * @param msg  The log message.
     * @param args The arguments of the log message.
     */
    fun verbose(msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.VERBOSE) {
            Log.d(sLogTag, String.format(Locale.US, msg!!, *args))
        }
    }

    /**
     * Does a verbose log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     */
    fun verbose(throwable: Throwable?, msg: String?) {
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, msg, throwable)
        }
    }

    /**
     * Does a verbose log.
     *
     * @param msgResId Resource id for the format string.
     */
    fun verbose(@StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, sAppContext!!.getString(msgResId))
        }
    }

    /**
     * Does a verbose log.
     *
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun verbose(@StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, sAppContext!!.getString(msgResId, *formatArgs))
        }
    }

    /**
     * Does a verbose log.
     *
     * @param throwable A throwable (error or exception)
     * @param msgResId  Resource id for the format string.
     */
    fun verbose(throwable: Throwable?, @StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, sAppContext!!.getString(msgResId), throwable)
        }
    }

    /**
     * Does a verbose log.
     *
     * @param throwable  A throwable (error or exception).
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun verbose(throwable: Throwable?, @StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.VERBOSE) {
            Log.v(sLogTag, sAppContext!!.getString(msgResId, *formatArgs), throwable)
        }
    }

    /**
     * Does a debug log.
     *
     * @param msg The log message.
     */
    fun debug(msg: String?) {
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, msg!!)
        }
    }

    /**
     * Does a debug log.
     *
     * @param msg  The log message.
     * @param args The arguments of the log message.
     */
    fun debug(msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, String.format(Locale.US, msg!!, *args))
        }
    }

    /**
     * Does a debug log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     */
    fun debug(throwable: Throwable?, msg: String?) {
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, msg, throwable)
        }
    }

    /**
     * Does a debug log.
     *
     * @param msgResId Resource id for the format string.
     */
    fun debug(@StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, sAppContext!!.getString(msgResId))
        }
    }

    /**
     * Does a debug log.
     *
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun debug(@StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, sAppContext!!.getString(msgResId, *formatArgs))
        }
    }

    /**
     * Does a debug log.
     *
     * @param throwable A throwable (error or exception)
     * @param msgResId  Resource id for the format string.
     */
    fun debug(throwable: Throwable?, @StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, sAppContext!!.getString(msgResId), throwable)
        }
    }

    /**
     * Does a debug log.
     *
     * @param throwable  A throwable (error or exception).
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun debug(throwable: Throwable?, @StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, sAppContext!!.getString(msgResId, *formatArgs), throwable)
        }
    }

    /**
     * Does an info log.
     *
     * @param msg The log message.
     */
    fun info(msg: String?) {
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, msg!!)
        }
    }

    /**
     * Does an info log.
     *
     * @param msg  The log message.
     * @param args Arguments of the log message.
     */
    fun info(msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, String.format(msg!!, *args))
        }
    }

    /**
     * Does an info log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     */
    fun info(throwable: Throwable?, msg: String?) {
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, msg, throwable)
        }
    }

    /**
     * Does an info log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     * @param args      Arguments of the log message.
     */
    fun info(throwable: Throwable?, msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, String.format(msg!!, *args), throwable)
        }
    }

    /**
     * Does an info log.
     *
     * @param msgResId Resource id for the format string.
     */
    fun info(@StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, sAppContext!!.getString(msgResId))
        }
    }

    /**
     * Does an info log.
     *
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun info(@StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, sAppContext!!.getString(msgResId, *formatArgs))
        }
    }

    /**
     * Does an info log.
     *
     * @param throwable A throwable (error or exception)
     * @param msgResId  Resource id for the format string.
     */
    fun info(throwable: Throwable?, @StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, sAppContext!!.getString(msgResId), throwable)
        }
    }

    /**
     * Does an info log.
     *
     * @param throwable  A throwable (error or exception).
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun info(throwable: Throwable?, @StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.INFO) {
            Log.i(sLogTag, sAppContext!!.getString(msgResId, *formatArgs), throwable)
        }
    }

    /**
     * Does a warning log.
     *
     * @param msg The log message.
     */
    fun warn(msg: String?) {
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, msg!!)
        }
    }

    /**
     * Does a warning log.
     *
     * @param msg  The log message.
     * @param args The arguments of the log message.
     */
    fun warn(msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, String.format(Locale.US, msg!!, *args))
        }
    }

    /**
     * Does a warning log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     */
    fun warn(throwable: Throwable?, msg: String?) {
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, msg, throwable)
        }
    }

    /**
     * Does a warning log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     * @param args      The arguments of the log message.
     */
    fun warn(throwable: Throwable?, msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, String.format(Locale.US, msg!!, *args), throwable)
        }
    }

    /**
     * Does a warning log.
     *
     * @param msgResId Resource id for the format string.
     */
    fun warn(@StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, sAppContext!!.getString(msgResId))
        }
    }

    /**
     * Does a warning log.
     *
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun warn(@StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, sAppContext!!.getString(msgResId, *formatArgs))
        }
    }

    /**
     * Does a warning log.
     *
     * @param throwable A throwable (error or exception)
     * @param msgResId  Resource id for the format string.
     */
    fun warn(throwable: Throwable?, @StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, sAppContext!!.getString(msgResId), throwable)
        }
    }

    /**
     * Does a warning log.
     *
     * @param throwable  A throwable (error or exception).
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun warn(throwable: Throwable?, @StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.WARN) {
            Log.w(sLogTag, sAppContext!!.getString(msgResId, *formatArgs), throwable)
        }
    }

    /**
     * Does an error log.
     *
     * @param msg The log message.
     */
    fun error(msg: String?) {
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, msg!!)
        }
    }

    /**
     * Does an error log.
     *
     * @param msg  The log message.
     * @param args The arguments of the log message.
     */
    fun error(msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, String.format(msg!!, *args))
        }
    }

    /**
     * Does an error log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     */
    fun error(throwable: Throwable?, msg: String?) {
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, msg, throwable)
        }
    }

    /**
     * Does an error log.
     *
     * @param throwable A throwable (error or exception).
     * @param msg       The log message.
     * @param args      The arguments of the log message.
     */
    fun error(throwable: Throwable?, msg: String?, vararg args: Any?) {
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, String.format(Locale.US, msg!!, *args), throwable)
        }
    }

    /**
     * Does an error log.
     *
     * @param msgResId Resource id for the format string.
     */
    fun error(@StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, sAppContext!!.getString(msgResId))
        }
    }

    /**
     * Does an error log.
     *
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun error(@StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, sAppContext!!.getString(msgResId, *formatArgs))
        }
    }

    /**
     * Does an error log.
     *
     * @param throwable A throwable (error or exception)
     * @param msgResId  Resource id for the format string.
     */
    fun error(throwable: Throwable?, @StringRes msgResId: Int) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, sAppContext!!.getString(msgResId), throwable)
        }
    }

    /**
     * Does an error log.
     *
     * @param throwable  A throwable (error or exception).
     * @param msgResId   Resource id for the format string.
     * @param formatArgs The format arguments that will be used for substitution.
     */
    fun error(throwable: Throwable?, @StringRes msgResId: Int, vararg formatArgs: Any?) {
        if (!checkAppContextSet()) {
            return
        }
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, sAppContext!!.getString(msgResId, *formatArgs), throwable)
        }
    }
}
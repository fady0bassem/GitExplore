package com.fadybassem.gitexplore.app

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorkerFactory
import com.fadybassem.gitexplore.BuildConfig
import com.fadybassem.gitexplore.data_layer.local.LanguagePreferences
import com.fadybassem.gitexplore.data_layer.local.LocaleUtil
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.TAG
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    val langaue: MutableState<String?> = mutableStateOf(null)

    var activityContext: Activity? = null

    private val isDark = mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        Logger.info(TAG, BuildConfig.APPLICATION_ID)
    }

    override fun attachBaseContext(base: Context) {
        langaue.value = LanguagePreferences(base).getPreferredLocale()
        super.attachBaseContext(LocaleUtil.getLocalizedContext(base, langaue.value!!))
    }

    fun toggleLightDarkTheme() {
        isDark.value = !isDark.value
    }

    fun setCurrentActivity(activity: Activity?) {
        activityContext = activity
    }

    fun getCurrentActivity() = activityContext
}


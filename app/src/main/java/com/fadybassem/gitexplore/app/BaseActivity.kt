package com.fadybassem.gitexplore.app

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.fadybassem.gitexplore.data_layer.local.LanguagePreferences
import com.fadybassem.gitexplore.data_layer.local.LocaleUtil
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.getActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : ComponentActivity() {

    private lateinit var oldPrefLocaleCode: String
    var language: MutableState<Language?> = mutableStateOf(null)

    @Inject
    lateinit var baseApplication: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resetTitle()
    }

    override fun onResume() {
        try {
            baseApplication.setCurrentActivity(activity = getActivity())
            super.onResume()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    /**
     * updates the toolbar text locale if it set from the android:label property of Manifest
     */
    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(
                componentName, PackageManager.GET_META_DATA
            ).labelRes
            if (label != 0) {
                setTitle(label)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        oldPrefLocaleCode = LanguagePreferences(newBase).getPreferredLocale()

        val currentLocaleCode = LanguagePreferences(newBase).getPreferredLocale()
        language.value =
            if (currentLocaleCode == Language.ENGLISH.lang) Language.ENGLISH else Language.ARABIC

        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
        super.attachBaseContext(newBase)
    }

    fun changeLanguage(changeCurrentLanguageInCurrentView: Boolean = true) {
        val locale =
            if (language.value?.lang == Language.ENGLISH.lang) Language.ARABIC else Language.ENGLISH
        LanguagePreferences(this).setPreferredLocale(locale.lang)
        LocaleUtil.applyLocalizedContext(applicationContext, locale.lang)
        language.value = locale

        if (changeCurrentLanguageInCurrentView) {
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }
    }
}
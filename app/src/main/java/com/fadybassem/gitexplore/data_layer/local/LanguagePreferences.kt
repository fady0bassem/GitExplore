package com.fadybassem.gitexplore.data_layer.local

import android.content.Context
import android.content.SharedPreferences
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.CHANGE_LANGUAGE_KEY
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.SHARED_PREFERENCE
import com.fadybassem.gitexplore.utils.Language

class LanguagePreferences(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCE, Context.MODE_PRIVATE
    )

    fun getPreferredLocale(): String {
        val locale = preferences.getString(CHANGE_LANGUAGE_KEY, Language.ENGLISH.lang)
        return if (locale.isNullOrEmpty()) {
            Language.ENGLISH.lang
        } else {
            locale.replace("\"", "")
        }
        //return preferences.getString(CHANGE_LANGUAGE_KEY, Language.ENGLISH.lang)!!.replace("\"", "")
    }

    fun setPreferredLocale(localeCode: String) {
        preferences.edit().putString(CHANGE_LANGUAGE_KEY, localeCode).apply()
    }
}
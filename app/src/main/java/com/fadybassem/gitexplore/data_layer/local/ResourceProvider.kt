package com.fadybassem.gitexplore.data_layer.local

import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceHelper: PreferenceHelper,
) {

    fun getString(resID: Int): String {
        return getStringByLocal(resID)
    }

    fun getString(resID: Int, value: String?): String {
        return getStringByLocal(resID, value)
    }

    fun getStringArray(resID: Int): Array<String> {
        return context.resources.getStringArray(resID)
    }

    private fun getStringByLocal(resId: Int): String {
        return getStringByLocalPlus17(resId, preferenceHelper.getSelectedLanguage().lang)
    }

    private fun getStringByLocal(resId: Int, value: String?): String {
        return getStringByLocalPlus17(resId, preferenceHelper.getSelectedLanguage().lang, value)
    }

    private fun getStringByLocalPlus17(resId: Int, locale: String): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(locale))
        return context.createConfigurationContext(configuration).resources.getString(resId)
    }

    private fun getStringByLocalPlus17(resId: Int, locale: String, value: String?): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(locale))
        return context.createConfigurationContext(configuration).resources.getString(resId, value)
    }
}

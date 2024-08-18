package com.fadybassem.gitexplore.data_layer.local

import android.content.SharedPreferences
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.CHANGE_LANGUAGE_KEY
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveData(key: String, value: Any?) {
        with(sharedPreferences.edit()) {
            putString(key, Gson().toJson(value)).apply()
        }
    }

    fun saveIntData(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value).apply()
        }
    }

    fun getSavedIntData(key: String): Int {
        return with(sharedPreferences) {
            getInt(key, 0)
        }
    }

    inner class ConvertType<T> {
        private fun getSavedData(key: String): String? {
            return with(sharedPreferences) {
                getString(key, null)
            }
        }

        fun convert(key: String, type: Class<T>): T? {
            var returnType: T? = null
            try {
                returnType = Gson().fromJson(getSavedData(key), type)
            } catch (e: Exception) {
                e.printStackTrace()
                clearUserData()
            }
            return returnType
        }
    }

    fun clearUserData(exceptLanguage: Boolean = true) {
        with(sharedPreferences.edit()) {
            if (exceptLanguage) {
                val lang = sharedPreferences.getString(CHANGE_LANGUAGE_KEY, "")
                clear().apply()
                putString(CHANGE_LANGUAGE_KEY, lang).apply()
            } else clear().apply()
        }
    }

    fun clearUserData() {
        with(sharedPreferences.edit()) {
            clear().apply()
        }
    }

    fun clearUserData(value: String) {
        with(sharedPreferences.edit()) {
            remove(value).apply()
        }
    }
}
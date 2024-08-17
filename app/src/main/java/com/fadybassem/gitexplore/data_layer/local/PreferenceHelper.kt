package com.fadybassem.gitexplore.data_layer.local

import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.CHANGE_LANGUAGE_KEY
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.utils.FIREBASE_INSTANCE_ID
import com.fadybassem.gitexplore.utils.IS_LOGGED_IN_KEY
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.TAG
import com.fadybassem.gitexplore.utils.USER
import java.util.Locale
import javax.inject.Inject

class PreferenceHelper @Inject constructor(
    private val preferenceUtils: PreferenceUtils,
) {
    fun setLanguage() {
        preferenceUtils.saveData(CHANGE_LANGUAGE_KEY, Language.ENGLISH.lang)
    }

    fun getSelectedLanguage(): Language {
        val lang =
            preferenceUtils.ConvertType<String>().convert(CHANGE_LANGUAGE_KEY, String::class.java)
        return if (lang.equals(Language.ARABIC.lang)) {
            Language.ARABIC
        } else {
            Language.ENGLISH
        }
    }

    fun getOppositeLanguage(): Language {
        val lang =
            preferenceUtils.ConvertType<String>().convert(CHANGE_LANGUAGE_KEY, String::class.java)
        return if (lang.equals(Language.ARABIC.lang)) {
            Language.ENGLISH
        } else {
            Language.ARABIC
        }
    }

    fun getDefaultLanguage(): Language =
        if (Locale.getDefault().language == Language.ARABIC.lang) Language.ARABIC else Language.ENGLISH

    fun changeApplicationLanguage() {
        preferenceUtils.saveData(
            CHANGE_LANGUAGE_KEY, if (getSelectedLanguage().lang == Language.ARABIC.lang) {
                Logger.debug(TAG, "Selected New Language To Arabic")
                Language.ENGLISH.lang
            } else {
                Logger.debug(TAG, "Selected New Language To English")
                Language.ARABIC.lang
            }
        )
    }

    fun isLoggedIn(): Boolean =
        preferenceUtils.ConvertType<Boolean>().convert(IS_LOGGED_IN_KEY, Boolean::class.java)
            ?: false

    fun setLoggedIn(isLoggedIn: Boolean) {
        preferenceUtils.saveData(IS_LOGGED_IN_KEY, isLoggedIn)
    }

    fun setFirebaseInstanceID(id: String) {
        preferenceUtils.saveData(FIREBASE_INSTANCE_ID, id)
    }

    fun getFirebaseInstanceID(): String? {
        return preferenceUtils.ConvertType<String>()
            .convert(FIREBASE_INSTANCE_ID, String::class.java)
    }

    fun setUser(user: User){
        preferenceUtils.saveData(USER, user)
    }

    fun getUser(): User? = preferenceUtils.ConvertType<User>().convert(USER, User::class.java)

    fun clearSavedData() {
        setLoggedIn(false)
        preferenceUtils.clearUserData()
    }

    fun clearSavedData(value: String) {
        setLoggedIn(false)
        preferenceUtils.clearUserData(value)
    }
}

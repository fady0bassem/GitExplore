package com.fadybassem.gitexplore.data_layer.local

import com.fadybassem.gitexplore.BuildConfig

class AppConfiguration {
    companion object {

        const val SPLASH_SCREEN_DELAY_DURATION = 2500L

        const val PASSWORD_LENGTH = 8

        const val CHANGE_LANGUAGE_KEY = "change_language_key"
        const val SHARED_PREFERENCE = "preferences.${BuildConfig.APPLICATION_ID}"
        const val LANGUAGE = "language"
        const val API_URL = "https://api.github.com/"
    }
}
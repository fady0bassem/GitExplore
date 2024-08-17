package com.fadybassem.gitexplore.data_layer.local

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.core.os.ConfigurationCompat
import com.fadybassem.gitexplore.utils.Language
import java.util.Locale

class LocaleUtil {
    companion object {
        private val supportedLocales = listOf(Language.ENGLISH.lang, Language.ARABIC.lang)
        private const val OPTION_PHONE_LANGUAGE = "sys_def"

        /**
         * returns the locale to use depending on the preference value
         * when preference value = "sys_def" returns the locale of current system
         * else it returns the locale code e.g. "en", "ar" etc.
         */
        private fun getLocaleFromPrefCode(prefCode: String): Locale {
            val localeCode = if (prefCode != OPTION_PHONE_LANGUAGE) {
                prefCode
            } else {
                val systemLang =
                    ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                        .get(0)?.language
                if (systemLang in supportedLocales) {
                    systemLang
                } else {
                    Language.ENGLISH.lang
                }
            }
            return Locale(localeCode ?: Language.ENGLISH.lang)
        }

        fun getLocalizedConfiguration(prefLocaleCode: String): Configuration {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            return getLocalizedConfiguration(locale)
        }

        private fun getLocalizedConfiguration(locale: Locale): Configuration {
            val config = Configuration()
            return config.apply {
                config.setLayoutDirection(locale)
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
            }
        }

        fun getLocalizedContext(baseContext: Context, prefLocaleCode: String): Context {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            return if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
                baseContext
            } else {
                baseContext
            }
        }

        fun applyLocalizedContext(baseContext: Context, prefLocaleCode: String) {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
                //baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            }
        }

        private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
            return configuration.locales.get(0)
        }

        fun getLocalizedResources(resources: Resources, prefLocaleCode: String): Resources {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            val config = resources.configuration
            @Suppress("DEPRECATION")
            config.locale = locale
            config.setLayoutDirection(locale)

            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
            return resources
        }
    }
}
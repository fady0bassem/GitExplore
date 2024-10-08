package com.fadybassem.gitexplore.utils

enum class Language(val lang: String) { ARABIC("ar"), ENGLISH("en") }

enum class Status { DEFAULT, LOADING, SUCCESS, ERROR, FAILED }

enum class LinkType { URL, EMAIL, PHONE, INTENT }

enum class SplashNavigation { Login, Main }
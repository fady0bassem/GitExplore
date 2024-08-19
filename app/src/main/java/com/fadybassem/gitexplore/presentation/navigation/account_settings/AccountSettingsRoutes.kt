package com.fadybassem.gitexplore.presentation.navigation.account_settings

sealed class AccountSettingsRoutes(val route: String) {
    data object Profile : AccountSettingsRoutes("profile")
}

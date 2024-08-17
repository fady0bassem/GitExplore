package com.fadybassem.gitexplore.presentation.navigation.splash

sealed class SplashRoutes(val route: String) {
    data object Splash : SplashRoutes("splash_screen")
}

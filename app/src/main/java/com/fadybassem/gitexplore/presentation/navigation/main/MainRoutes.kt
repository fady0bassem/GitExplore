package com.fadybassem.gitexplore.presentation.navigation.main

sealed class MainRoutes(val route: String) {
    data object Listing : MainRoutes("listing_screen")
    data object Details : MainRoutes("repository_screen")
}
package com.fadybassem.gitexplore.presentation.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fadybassem.gitexplore.presentation.navigation.main.MainNavigation
import com.fadybassem.gitexplore.presentation.navigation.main.MainRoutes
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.NAVIGATE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = intent.extras?.getString(NAVIGATE) ?: MainRoutes.Listing.route

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = White
            ) {
                MainNavigation(startDestination = startDestination)
            }
        }
    }
}
package com.fadybassem.gitexplore.presentation.navigation.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.screens.main.listing.ListingScreen
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.showSystemUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(startDestination: String) {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity

    var currentScreen by remember { mutableStateOf(startDestination) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = when (currentScreen) {
                    MainRoutes.Listing.route -> stringResource(id = R.string.listing_title)
                    else -> ""
                },
                color = Black,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        }, navigationIcon = {
            if (currentScreen != MainRoutes.Listing.route) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                null
            }
        })
    }) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            // Listing Screen
            composable(MainRoutes.Listing.route) {
                currentScreen = MainRoutes.Listing.route
                ListingScreen(navController = navController, finishActivity = { activity.finish() })
            }
        }
    }



    BackHandler {
        when (navController.currentDestination?.route) {
            MainRoutes.Listing.route -> {
                activity.finish() // Close the app
            }

            else -> {
                navController.navigateUp() // Handle back navigation within the app
            }
        }
    }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Logger.debug(destination.route)

        currentScreen = destination.route ?: MainRoutes.Listing.route

        when (destination.route) {
            MainRoutes.Listing.route -> {
                activity.showSystemUI()
            }
        }
    }
}
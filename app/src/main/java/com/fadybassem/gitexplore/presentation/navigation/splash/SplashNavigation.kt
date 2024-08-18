package com.fadybassem.gitexplore.presentation.navigation.splash

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationRoutes
import com.fadybassem.gitexplore.presentation.navigation.startSingleNavigation
import com.fadybassem.gitexplore.presentation.ui.authentication.AuthenticationActivity
import com.fadybassem.gitexplore.presentation.ui.main.MainActivity
import com.fadybassem.gitexplore.presentation.ui.splash.splash_screen.SplashScreen
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.NAVIGATE
import com.fadybassem.gitexplore.utils.SplashNavigation.Login
import com.fadybassem.gitexplore.utils.SplashNavigation.Main
import com.fadybassem.gitexplore.utils.hideSystemUI
import com.fadybassem.gitexplore.utils.showSystemUI

@Composable
fun SplashNavigation() {

    val navController = rememberNavController()
    val activity = LocalContext.current as Activity

    NavHost(navController = navController, startDestination = SplashRoutes.Splash.route) {
        // splash screen
        composable(SplashRoutes.Splash.route) {
            SplashScreen(navigation = { launcherNavigation ->
                when (launcherNavigation) {
                    Login -> {
                        // navigate to login screen
                        activity.startSingleNavigation(
                            destination = AuthenticationActivity::class.java,
                            bundle = bundleOf(NAVIGATE to AuthenticationRoutes.Login.route)
                        )
                    }

                    Main -> {
                        // start main activity - main screen
                        activity.startSingleNavigation(MainActivity::class.java)
                    }
                }
            }, finishActivity = {
                activity.finish()
            })
        }
    }

    BackHandler {
        navController.navigateUp() // Handle back navigation within the app
    }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Logger.debug(destination.route)

        when (destination.route) {
            SplashRoutes.Splash.route -> {
                activity.hideSystemUI()
            }

            else -> {
                activity.showSystemUI()
            }
        }
    }
}
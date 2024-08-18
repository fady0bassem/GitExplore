package com.fadybassem.gitexplore.presentation.navigation.authentication

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fadybassem.gitexplore.presentation.navigation.startSingleNavigation
import com.fadybassem.gitexplore.presentation.ui.authentication.forgot_pssword_screen.ForgotPasswordScreen
import com.fadybassem.gitexplore.presentation.ui.authentication.login_screen.LoginScreen
import com.fadybassem.gitexplore.presentation.ui.authentication.register_screen.RegisterScreen
import com.fadybassem.gitexplore.presentation.ui.main.MainActivity
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.showSystemUI

@Composable
fun AuthenticationNavigation(startDestination: String) {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity

    NavHost(navController = navController, startDestination = startDestination) {
        // Login Screen
        composable(AuthenticationRoutes.Login.route) {
            LoginScreen(
                navController = navController,
                finishActivity = { activity.finish() },
                navigation = {
                    activity.startSingleNavigation(MainActivity::class.java)
                })
        }

        // Register Screen
        composable(AuthenticationRoutes.Register.route) {
            RegisterScreen(navController = navController, finishActivity = { activity.finish() })
        }

        // Forgot password Screen
        composable(AuthenticationRoutes.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }
    }

    BackHandler {
        when (navController.currentDestination?.route) {
            AuthenticationRoutes.Login.route -> {
                activity.finish() // Close the app
            }

            else -> {
                navController.navigateUp() // Handle back navigation within the app
            }
        }
    }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Logger.debug(destination.route)

        when (destination.route) {
            AuthenticationRoutes.Login.route -> {
                activity.showSystemUI()
            }

            AuthenticationRoutes.Register.route -> {
                activity.showSystemUI()
            }

            AuthenticationRoutes.ForgotPassword.route -> {
                activity.showSystemUI()
            }
        }
    }
}
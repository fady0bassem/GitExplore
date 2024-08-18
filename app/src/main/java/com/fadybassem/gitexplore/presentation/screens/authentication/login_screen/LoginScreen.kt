package com.fadybassem.gitexplore.presentation.screens.authentication.login_screen

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.app.BaseActivity
import com.fadybassem.gitexplore.presentation.dialogs.SimpleInfoDialog
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationRoutes
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents

@Composable
fun LoginScreen(
    navController: NavHostController,
    finishActivity: () -> Unit,
    navigation: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val showFirebaseErrorDialog = viewModel.showFirebaseErrorDialog

    val navigateToMainScreen = remember { viewModel.navigateToMainScreen }
    val showErrorDialog = remember { viewModel.showErrorDialog }

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoginView(language = remember { viewModel.language },
                version = viewModel.version,
                emailTextState = remember { viewModel.emailTextState },
                passwordTextState = remember { viewModel.passwordTextState },
                passwordVisibility = remember { viewModel.passwordVisibility },
                emailTextStateError = remember { viewModel.emailTextStateError },
                passwordTextStateError = remember { viewModel.passwordTextStateError },
                onLanguageChange = {
                    val baseActivity = activity as BaseActivity
                    baseActivity.changeLanguage()
                },
                onValidateEmail = { viewModel.onValidateEmail() },
                onValidatePassword = { viewModel.validatePassword() },
                onClickSignIn = { viewModel.loginClick() },
                onClickSignOut = {
                    navController.navigate(AuthenticationRoutes.Register.route)
                },
                onClickForgotPassword = {
                    navController.navigate(AuthenticationRoutes.ForgotPassword.route)
                })

            // firebase error
            if (showFirebaseErrorDialog.value) {
                SimpleInfoDialog(titleText = stringResource(id = R.string.error),
                    infoText = stringResource(id = R.string.generic_error),
                    showSuccessImage = false,
                    actionText = stringResource(id = R.string.okay),
                    onActionClick = {
                        viewModel.showFirebaseErrorDialog.value = false
                        finishActivity.invoke()
                    },
                    onDismiss = {
                        viewModel.showFirebaseErrorDialog.value = false
                        finishActivity.invoke()
                    })
            }

            // login error
            if (showErrorDialog.value.first == true) {
                SimpleInfoDialog(infoText = showErrorDialog.value.second ?: "",
                    actionText = stringResource(id = R.string.ok),
                    onDismiss = { viewModel.showErrorDialog.value = Pair(null, null) },
                    onActionClick = { viewModel.showErrorDialog.value = Pair(null, null) })
            }

            // navigate to main screen
            if (navigateToMainScreen.value == true) {
                viewModel.navigateToMainScreen.value = null
                navigation.invoke()
            }
        }
    }
}
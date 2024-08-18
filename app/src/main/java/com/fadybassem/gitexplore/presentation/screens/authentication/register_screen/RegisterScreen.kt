package com.fadybassem.gitexplore.presentation.screens.authentication.register_screen

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.dialogs.SimpleInfoDialog
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationRoutes
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.showToastMessage

@Composable
fun RegisterScreen(
    navController: NavHostController,
    finishActivity: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val showFirebaseErrorDialog = viewModel.showFirebaseErrorDialog

    val registerResponse = remember { viewModel.registerResponse }

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            RegisterView(
                firstNameTextState = remember { viewModel.firstNameTextState },
                lastNameTextState = remember { viewModel.lastNameTextState },
                emailTextState = remember { viewModel.emailTextState },
                passwordTextState = remember { viewModel.passwordTextState },
                confirmPasswordTextState = remember { viewModel.confirmPasswordTextState },
                passwordVisibility = remember { viewModel.passwordVisibility },
                confirmPasswordVisibility = remember { viewModel.confirmPasswordVisibility },
                firstNameTextStateError = remember { viewModel.firstNameTextStateError },
                lastNameTextStateError = remember { viewModel.lastNameTextStateError },
                emailTextStateError = remember { viewModel.emailTextStateError },
                passwordTextStateError = remember { viewModel.passwordTextStateError },
                confirmPasswordTextStateError = remember { viewModel.confirmPasswordTextStateError },
                passwordMatchTextStateError = remember { viewModel.passwordMatchTextStateError },
                onValidateFirstName = { viewModel.validateFirstName() },
                onValidateLastName = { viewModel.validateLastName() },
                onValidateEmail = { viewModel.validateEmail() },
                onValidatePassword = { viewModel.validatePassword() },
                onValidateConfirmPassword = { viewModel.validateConfirmPassword() },
                onValidatePasswordMatch = { viewModel.validatePasswordMatch() },
                onClickSignUp = { viewModel.signUp() },
            )

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

            if (registerResponse.value.first == true) {
                // navigate back to login screen
                context.showToastMessage(registerResponse.value.second)
                viewModel.registerResponse.value = Pair(null, null)
                navController.popBackStack(
                    route = AuthenticationRoutes.Login.route,
                    inclusive = false
                )
            } else if (registerResponse.value.first == false) {
                context.showToastMessage(registerResponse.value.second)
                viewModel.registerResponse.value = Pair(null, null)
            }
        }
    }
}
package com.fadybassem.gitexplore.presentation.ui.authentication.forgot_pssword_screen

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
import com.fadybassem.gitexplore.presentation.dialogs.SimpleInfoDialog
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationRoutes
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.showToastMessage

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {

    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val forgotPasswordResponse = remember { viewModel.forgotPasswordResponse }

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            ForgotPasswordView(emailTextState = viewModel.emailTextState,
                emailTextStateError = viewModel.emailTextStateError,
                onValidateEmail = { viewModel.onValidateEmail() },
                onClickSubmit = { viewModel.submitClick() })

            if (forgotPasswordResponse.value.first == true) {
                SimpleInfoDialog(infoText = forgotPasswordResponse.value.second ?: "",
                    actionText = stringResource(id = R.string.ok),
                    onDismiss = {
                        viewModel.forgotPasswordResponse.value = Pair(null, null)
                    },
                    onActionClick = {
                        // navigate back to login screen
                        viewModel.forgotPasswordResponse.value = Pair(null, null)
                        navController.popBackStack(
                            route = AuthenticationRoutes.Login.route,
                            inclusive = false
                        )
                    })
            } else if (forgotPasswordResponse.value.first == false) {
                context.showToastMessage(forgotPasswordResponse.value.second)
                viewModel.forgotPasswordResponse.value = Pair(null, null)
            }
        }
    }
}
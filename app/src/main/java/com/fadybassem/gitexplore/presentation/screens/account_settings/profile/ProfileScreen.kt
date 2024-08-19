package com.fadybassem.gitexplore.presentation.screens.account_settings.profile

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
import com.fadybassem.gitexplore.presentation.dialogs.ConfirmDialog
import com.fadybassem.gitexplore.presentation.dialogs.SimpleInfoDialog
import com.fadybassem.gitexplore.presentation.navigation.startBaseNavigation
import com.fadybassem.gitexplore.presentation.screens.authentication.AuthenticationActivity
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.showToastMessage

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val showConfirmDialog = remember { mutableStateOf(false) }

    val navigateToMainScreen = remember { viewModel.navigateToMainScreen }
    val showErrorDialog = remember { viewModel.showErrorDialog }

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            ProfileView(user = viewModel.user, onLocgoutClick = {
                showConfirmDialog.value = true
            })

            if (showConfirmDialog.value) {
                ConfirmDialog(titleText = stringResource(id = R.string.logout),
                    infoText = stringResource(id = R.string.logout_dialog_confirmation),
                    actionPositiveText = stringResource(id = R.string.okay),
                    actionNegativeText = stringResource(id = R.string.cancel),
                    onPositiveActionClick = {
                        showConfirmDialog.value = false
                        viewModel.logout()
                    },
                    onNegativeActionClick = { showConfirmDialog.value = false },
                    onDismiss = { showConfirmDialog.value = false })
            }

            // logout error
            if (showErrorDialog.value.first == true) {
                SimpleInfoDialog(infoText = showErrorDialog.value.second ?: "",
                    actionText = stringResource(id = R.string.ok),
                    onDismiss = { viewModel.showErrorDialog.value = Pair(null, null) },
                    onActionClick = { viewModel.showErrorDialog.value = Pair(null, null) })
            }

            // navigate to authentication screen
            if (navigateToMainScreen.value == true) {
                context.showToastMessage(stringResource(id = R.string.logout_successfully))
                activity.startBaseNavigation(AuthenticationActivity::class.java)
            }
        }
    }
}
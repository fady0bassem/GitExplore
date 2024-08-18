package com.fadybassem.gitexplore.presentation.screens.splash.splash_screen

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
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.dialogs.SimpleInfoDialog
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.SplashNavigation

@Composable
fun SplashScreen(
    navigation: (SplashNavigation) -> Unit,
    finishActivity: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val startNavigation = remember { viewModel.startNavigation }

    val showFirebaseErrorDialog = viewModel.showFirebaseErrorDialog

    AppTheme(apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            SplashView()

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
        }
    }

    startNavigation.value?.let { launcherNavigation ->
        navigation.invoke(launcherNavigation)
    }
}
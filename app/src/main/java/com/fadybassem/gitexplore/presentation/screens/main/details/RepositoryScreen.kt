package com.fadybassem.gitexplore.presentation.screens.main.details

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.showToastMessage

@Composable
fun RepositoryScreen(
    navController: NavHostController,
    viewModel: RepositoryViewModel = hiltViewModel(),
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val showApiError = viewModel.showApiError

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            RepositoryView(repository = viewModel.repository)

            // show api error toast
            if (showApiError.value.first) {
                context.showToastMessage(showApiError.value.second)
                viewModel.showApiError.value = Pair(false, null)
            }
        }
    }
}
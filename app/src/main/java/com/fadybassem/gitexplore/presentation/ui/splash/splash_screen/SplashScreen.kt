package com.fadybassem.gitexplore.presentation.ui.splash.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.SPLASH_SCREEN_DELAY_DURATION
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.SplashNavigation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigation: (SplashNavigation) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val startNavigation = remember { viewModel.startNavigation }

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        SplashView()
    }

    LaunchedEffect(Unit) {
        delay(SPLASH_SCREEN_DELAY_DURATION)
    }

    startNavigation.value?.let { launcherNavigation ->
        navigation.invoke(launcherNavigation)
    }
}
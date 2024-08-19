package com.fadybassem.gitexplore.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.fadybassem.gitexplore.presentation.components.network_state.ConnectivityStatus
import com.fadybassem.gitexplore.presentation.components.progressbar.CircularIndeterminateProgressBar
import com.fadybassem.gitexplore.presentation.components.snackbar.DefaultSnackBar
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.Status
import com.fadybassem.gitexplore.utils.disableTouch
import com.fadybassem.gitexplore.utils.enableTouch
import com.fadybassem.gitexplore.utils.hideKeyboard

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    language: Language = Language.ENGLISH,
    apiStatus: Status? = null,
    scaffoldState: SnackbarHostState? = null,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val colorScheme = lightScheme /*when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }*/

    val typography = AppTypography

    MaterialTheme(colorScheme = colorScheme, typography = typography) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ConnectivityStatus()
                content.invoke()
            }

            CircularIndeterminateProgressBar(isDisplayed = apiStatus == Status.LOADING)

            scaffoldState?.let { state ->
                DefaultSnackBar(modifier = Modifier.align(Alignment.BottomCenter),
                    snackBarHostState = state,
                    onDismiss = {
                        state.currentSnackbarData?.dismiss()
                    })
            }

            when (apiStatus) {
                Status.LOADING -> {
                    activity?.disableTouch()
                    activity?.hideKeyboard()
                }

                else -> {
                    activity?.enableTouch()
                    activity?.hideKeyboard()
                }
            }
        }
    }
}
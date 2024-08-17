package com.fadybassem.gitexplore.presentation.components.screen_size

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current

    val windowDimensions = Dimensions()

    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> {
                windowDimensions.horizontalSpacing = 8.dp
                windowDimensions.horizontalPadding = 16.dp
                windowDimensions.buttonPadding =
                    (configuration.screenWidthDp.dp - (configuration.screenWidthDp.dp - windowDimensions.horizontalPadding)) * 4
                WindowInfo.WindowType.Compact
            }

            configuration.screenWidthDp < 840 -> {
                windowDimensions.horizontalSpacing = 8.dp
                windowDimensions.horizontalPadding = 20.dp
                windowDimensions.buttonPadding =
                    (configuration.screenWidthDp.dp - (configuration.screenWidthDp.dp - windowDimensions.horizontalPadding)) * 4
                WindowInfo.WindowType.Medium
            }

            else -> {
                windowDimensions.horizontalSpacing = 12.dp
                windowDimensions.horizontalPadding = 24.dp
                windowDimensions.buttonPadding =
                    (configuration.screenWidthDp.dp - (configuration.screenWidthDp.dp - windowDimensions.horizontalPadding)) * 4
                WindowInfo.WindowType.Expanded
            }
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> {
                windowDimensions.verticalSpacing = 6.dp
                windowDimensions.verticalPadding = 8.dp
                WindowInfo.WindowType.Compact
            }

            configuration.screenHeightDp < 900 -> {
                windowDimensions.verticalSpacing = 8.dp
                windowDimensions.verticalPadding = 8.dp
                WindowInfo.WindowType.Medium
            }

            else -> {
                windowDimensions.verticalSpacing = 10.dp
                windowDimensions.verticalPadding = 8.dp
                WindowInfo.WindowType.Expanded
            }
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp,
        widthPixels = configuration.screenWidthDp,
        heightPixels = configuration.screenHeightDp,
        windowDimensions = windowDimensions
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val widthPixels: Int,
    val heightPixels: Int,
    val windowDimensions: Dimensions
) {
    sealed class WindowType {
        data object Compact : WindowType()
        data object Medium : WindowType()
        data object Expanded : WindowType()
    }
}
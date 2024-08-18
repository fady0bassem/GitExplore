package com.fadybassem.gitexplore.presentation.ui.splash.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashPreview() {
    AppTheme {
        SplashView()
    }
}

@Composable
internal fun SplashView() {

    val windowInfo = rememberWindowInfo()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.colored_logo),
            contentDescription = "splash logo",
            modifier = Modifier.fillMaxSize()
                .padding(windowInfo.windowDimensions.verticalPadding * 2),
            contentScale = ContentScale.Inside,
        )
    }
}
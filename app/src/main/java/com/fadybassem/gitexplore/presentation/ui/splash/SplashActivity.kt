package com.fadybassem.gitexplore.presentation.ui.splash

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fadybassem.gitexplore.app.BaseActivity
import com.fadybassem.gitexplore.presentation.navigation.splash.SplashNavigation
import com.fadybassem.gitexplore.presentation.theme.White

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = White
            ) {
                SplashNavigation()
            }
        }
    }
}
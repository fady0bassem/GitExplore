package com.fadybassem.gitexplore.presentation.ui.authentication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.fadybassem.gitexplore.app.BaseActivity
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationNavigation
import com.fadybassem.gitexplore.presentation.navigation.authentication.AuthenticationRoutes
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.NAVIGATE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = intent.extras?.getString(NAVIGATE) ?: AuthenticationRoutes.Login.route

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = White
            ) {
                AuthenticationNavigation(startDestination = startDestination)
            }
        }
    }
}
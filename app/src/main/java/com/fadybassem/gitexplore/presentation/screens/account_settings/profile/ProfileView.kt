package com.fadybassem.gitexplore.presentation.screens.account_settings.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.presentation.components.button.TransparentOutlinedButton
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.utils.Language

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "en")
@Composable
private fun ForgotPasswordPreview() {
    AppTheme(language = Language.ENGLISH) {
        ProfileView(
            user = User(
                displayName = " John Doe", email = "john@yahoo.com", providerId = "123"
            ), onLocgoutClick = {}
        )
    }
}

@Composable
internal fun ProfileView(user: User?, onLocgoutClick:()-> Unit) {
    val windowInfo = rememberWindowInfo()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 2,
                    end = windowInfo.windowDimensions.verticalPadding * 2
                )
        ) {

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

            AsyncImage(
                modifier = Modifier
                    .size(windowInfo.windowDimensions.verticalPadding * 25)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally),
                model = ImageRequest.Builder(LocalContext.current).data(user?.photoUrl)
                    .error(R.drawable.place_holder).fallback(R.drawable.place_holder).build(),
                contentDescription = stringResource(id = R.string.content_description),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

            user?.displayName?.let {
                androidx.compose.material3.Text(
                    text = "${stringResource(id = R.string.display_name)}: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

            user?.email?.let {
                androidx.compose.material3.Text(
                    text = "${stringResource(id = R.string.user_email)}: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

            user?.providerId?.let {
                androidx.compose.material3.Text(
                    text = "${stringResource(id = R.string.provider_id)}: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // logout
            TransparentOutlinedButton(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 10,
                    end = windowInfo.windowDimensions.verticalPadding * 10
                )
                .align(alignment = Alignment.CenterHorizontally),
                textModifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding),
                text = stringResource(id = R.string.logout),
                shapeSize = windowInfo.windowDimensions.verticalPadding * 2,
                containerColor = MaterialTheme.colorScheme.primary,
                //contentColor = SignOutButtonContentColor,
                textColor = Black,
                textStyle = MaterialTheme.typography.bodySmall,
                onClick = {
                    onLocgoutClick.invoke()
                })
        }
    }
}
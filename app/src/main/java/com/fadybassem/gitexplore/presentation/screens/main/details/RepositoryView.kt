package com.fadybassem.gitexplore.presentation.screens.main.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.models.github.Owner
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Gold
import com.fadybassem.gitexplore.utils.Language

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun ListingPreview() {
    AppTheme(language = Language.ENGLISH) {
        RepositoryView(repository = remember {
            mutableStateOf(
                Repository(
                    id = 1,
                    name = "grit",
                    fullName = "mojombo/grit",
                    owner = Owner(
                        login = "mojombo",
                        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                    ),
                    description = "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
                    stargazersCount = 2
                )
            )
        })
    }
}

@Composable
internal fun RepositoryView(repository: MutableState<Repository?>) {
    val windowInfo = rememberWindowInfo()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            model = ImageRequest.Builder(LocalContext.current)
                .data(repository.value?.owner?.avatarUrl).error(R.drawable.place_holder)
                .fallback(R.drawable.place_holder).build(),
            contentDescription = stringResource(id = R.string.content_description),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        repository.value?.stargazersCount?.let {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(
                        id = R.string.content_description, "stars"
                    ),
                    tint = Gold
                )

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.horizontalPadding / 4))

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "$it",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        }

        repository.value?.name?.let {
            Text(
                text = "${stringResource(id = R.string.repository_name)}: $it",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        repository.value?.fullName?.let {
            Text(
                text = "${stringResource(id = R.string.repository_full_name)}: $it",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        repository.value?.description?.let {
            Text(
                text = "${stringResource(id = R.string.repository_description)}: $it",
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

    }

}
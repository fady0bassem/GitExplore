package com.fadybassem.gitexplore.presentation.screens.main.listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.models.github.Owner
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.Language

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun ListingPreview() {
    AppTheme(language = Language.ARABIC) {
        RepositoryItemView(repository = Repository(
            id = 1,
            name = "grit",
            fullName = "mojombo/grit",
            owner = Owner(
                login = "mojombo",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            ),
            description = "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
            stargazersCount = 2
        ), onItemClick = {})
    }
}


@Composable
internal fun RepositoryItemView(repository: Repository, onItemClick: (Int) -> Unit) {

    val windowInfo = rememberWindowInfo()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = windowInfo.windowDimensions.verticalPadding * 2,
                end = windowInfo.windowDimensions.verticalPadding * 2
            )
            .clickable { onItemClick.invoke(repository.id) },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(windowInfo.windowDimensions.verticalPadding * 2),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

        Row(
            modifier = Modifier.padding(
                start = windowInfo.windowDimensions.verticalPadding,
                end = windowInfo.windowDimensions.verticalPadding
            ), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(windowInfo.windowDimensions.verticalPadding * 6)
                    .clip(CircleShape)
                    .background(Color.Gray),
                model = ImageRequest.Builder(LocalContext.current).data(repository.owner.avatarUrl)
                    .error(R.drawable.place_holder).fallback(R.drawable.place_holder).build(),
                contentDescription = stringResource(id = R.string.content_description),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.horizontalPadding / 2))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "${stringResource(id = R.string.repository_name)}: ${repository.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${stringResource(id = R.string.repository_full_name)}: ${repository.fullName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                repository.description?.let {
                    Text(
                        text = "${stringResource(id = R.string.repository_description)}: $it",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.horizontalPadding / 2))

            Icon(
                modifier = Modifier.size(windowInfo.windowDimensions.verticalPadding * 2),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = stringResource(
                    id = R.string.content_description, "repository details"
                ),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))
    }
}
package com.fadybassem.gitexplore.presentation.screens.main.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.models.github.Owner
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.components.text.EmptyListText
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedLabeledOutlinedTextField
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun ListingPreview() {
    // Create a dummy PagingData for preview
    val dummyPagingData = Pager(PagingConfig(pageSize = 30)) {
        FakePagingSource()
    }.flow.collectAsLazyPagingItems()

    AppTheme(language = Language.ARABIC, apiStatus = Status.LOADING) {
        ListingView(user = User(displayName = "Fady", photoUrl = ""),
            searchQuery = remember { mutableStateOf("") },
            repositoriesList = dummyPagingData,
            onSearchClick = {},
            clearSearchQuery = {},
            onItemClick = {},
            navigateToProfile = {})
    }
}

// Fake PagingSource to provide dummy data for preview
class FakePagingSource : PagingSource<Int, Repository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return LoadResult.Page(
            data = List(0) { index ->
                Repository(
                    id = index,
                    name = "Repository $index",
                    fullName = "mojombo/grit",
                    owner = Owner(
                        login = "mojombo",
                        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                    ),
                    description = "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
                    stargazersCount = 2
                )
            }, prevKey = null, nextKey = 1 // Adjust based on your paging logic
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int {
        return 0
    }
}

@Composable
internal fun ListingView(
    user: User?,
    searchQuery: MutableState<String>,
    repositoriesList: LazyPagingItems<Repository>,
    onSearchClick: () -> Unit,
    clearSearchQuery: () -> Unit,
    onItemClick: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    val windowInfo = rememberWindowInfo()
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val showClearIcon by remember {
        derivedStateOf {
            searchQuery.value.isNotEmpty()
        }
    }

    val searchQueryState = rememberUpdatedState(searchQuery.value)
    LaunchedEffect(searchQuery.value) {
        launch {
            delay(300) // Half second delay
            if (searchQueryState.value == searchQuery.value) {
                if (searchQuery.value.isNotBlank()) {
                    onSearchClick()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 2,
                    end = windowInfo.windowDimensions.verticalPadding * 2
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(user?.photoUrl)
                    .error(R.drawable.colored_logo).fallback(R.drawable.colored_logo).build(),
                contentDescription = stringResource(
                    id = R.string.content_description, "Profile picture"
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(windowInfo.windowDimensions.verticalPadding * 8)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable{
                        navigateToProfile.invoke()
                    }
            )

            Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

            // Welcome text
            Text(
                text = "${stringResource(id = R.string.welcome)}, ${user?.displayName}",
                color = Black,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        // Search input field
        BorderedLabeledOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 2,
                    end = windowInfo.windowDimensions.verticalPadding * 2
                ),
            textState = searchQuery,
            textStateError = null,
            label = stringResource(id = R.string.search),
            showLeadingIcon = true,
            leadingIcon = Icons.Filled.Search,
            leadingIconTint = Color.Black,
            showTrailingIcon = showClearIcon,
            trailingIcon = Icons.Filled.Close,
            trailingIconTint = Color.Black,
            trailingIconClickable = true,
            onTrailingIconClick = {
                localFocusManager.clearFocus()
                keyboardController?.hide()
                clearSearchQuery.invoke()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                if (searchQuery.value.isNotBlank()) {
                    localFocusManager.clearFocus()
                    keyboardController?.hide()
                    onSearchClick.invoke()
                }
            })
        )

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        if (repositoriesList.itemCount == 0) {
            EmptyListText()
        }

        // Display the list of repositories
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(repositoriesList.itemCount) { index ->
                val repository = repositoriesList[index]
                repository?.let {
                    RepositoryItemView(repository = it, onItemClick = { onItemClick.invoke(it) })
                    Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))
                }
            }
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))


    }
}

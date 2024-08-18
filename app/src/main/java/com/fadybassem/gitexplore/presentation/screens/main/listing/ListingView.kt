package com.fadybassem.gitexplore.presentation.screens.main.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.components.text.EmptyListText
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedLabeledOutlinedTextField
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.utils.Language

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun ListingPreview() {
    AppTheme(language = Language.ARABIC) {
        ListingView(user = User(displayName = "Fady", photoUrl = ""),
            searchQuery = remember { mutableStateOf("") },
            repositoriesList = remember { mutableStateListOf() },
            isSearch = false,
            scrollStatus = remember { mutableStateOf(false) },
            shouldScrollToTop = remember { mutableStateOf(false) },
            paginatePublicRepositories = {},
            clearSearchQuery = {},
            onSearchClick = {},
            paginateSearchRepositories = {})
    }
}

@Composable
internal fun ListingView(
    user: User?,
    searchQuery: MutableState<String>,
    repositoriesList: SnapshotStateList<Repository>,
    isSearch: Boolean,
    scrollStatus: MutableState<Boolean>,
    shouldScrollToTop: MutableState<Boolean>,
    paginatePublicRepositories: () -> Unit,
    clearSearchQuery: () -> Unit,
    onSearchClick: () -> Unit,
    paginateSearchRepositories: () -> Unit,
) {
    val windowInfo = rememberWindowInfo()

    val listState = rememberLazyListState()

    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val showClearIcon by remember {
        derivedStateOf {
            searchQuery.value.isNotEmpty()
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleItemIndex != null && lastVisibleItemIndex >= listState.layoutInfo.totalItemsCount - 10
        }
    }

    // Load more data when the user is close to the end
    LaunchedEffect(shouldLoadMore.value) {
        if (isSearch) {
            paginateSearchRepositories.invoke()
        } else {
            paginatePublicRepositories.invoke()
        }
    }

    // Scroll to the top when the list changes
    LaunchedEffect(shouldScrollToTop.value) {
        if (shouldScrollToTop.value) {
            listState.scrollToItem(0)
            // Reset the flag to avoid scrolling repeatedly
            shouldScrollToTop.value = false
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
                modifier = Modifier
                    .size(windowInfo.windowDimensions.verticalPadding * 8)
                    .clip(CircleShape)
                    .background(Color.Gray),
                model = ImageRequest.Builder(LocalContext.current).data(user?.photoUrl)
                    .error(R.drawable.colored_logo).fallback(R.drawable.colored_logo).build(),
                contentDescription = stringResource(
                    id = R.string.content_description, "Profile picture"
                ),
                contentScale = ContentScale.Crop
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

        // search input
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
                localFocusManager.clearFocus()
                keyboardController?.hide()
                onSearchClick.invoke()
            })
        )

        if (repositoriesList.size == 0) {
            EmptyListText()
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
            itemsIndexed(items = repositoriesList) { index, repository ->
                RepositoryItemView(repository = repository)

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))
            }
        }

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))
    }
}
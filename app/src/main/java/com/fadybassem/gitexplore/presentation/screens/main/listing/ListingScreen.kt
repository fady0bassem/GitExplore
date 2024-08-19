package com.fadybassem.gitexplore.presentation.screens.main.listing

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.fadybassem.gitexplore.presentation.navigation.main.MainRoutes
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.utils.ObserveLifecycleEvents
import com.fadybassem.gitexplore.utils.showToastMessage

@Composable
fun ListingScreen(
    navController: NavHostController,
    navigateToProfile: () -> Unit,
    viewModel: ListingViewModel = hiltViewModel(),
) {

    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val context = LocalContext.current
    val activity = context as Activity

    val language = remember { viewModel.language }
    val apiStatus = remember { viewModel.apiStatus }

    val showApiError = viewModel.showApiError
    val user = viewModel.user
    val searchQuery = viewModel.searchQuery

    val publicRepoItems = viewModel.publicRepoFlow.collectAsLazyPagingItems()
    val searchRepoItems = viewModel.searchPageFlow.collectAsLazyPagingItems()

    val isSearch = searchQuery.value.isNotEmpty()

    AppTheme(language = language.value, apiStatus = apiStatus.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            ListingView(user = user,
                searchQuery = searchQuery,
                repositoriesList = if (isSearch) searchRepoItems else publicRepoItems,
                onSearchClick = { viewModel.onSearchClick() },
                clearSearchQuery = { viewModel.clearSearchQuery() },
                onItemClick = {
                    navController.navigate(MainRoutes.Details.route + "/${it}")
                },
                navigateToProfile = {
                    navigateToProfile.invoke()
                })
        }

        // show api error toast
        if (showApiError.value.first) {
            context.showToastMessage(showApiError.value.second)
            viewModel.showApiError.value = Pair(false, null)
        }
    }
}

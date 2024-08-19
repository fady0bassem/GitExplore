# GitExplore

this app authenticate users with Email/Password. Also, the App allows the user to Login/Register using his/her basic info.
Note: Use Firebase Auth
The app lists all public repos using GitHub API. Also, the app have a search feature to allow the user to search for a repo using repo name. Also, the app take care of pagination and handle any network error.
#
Project Architecture:
- MVVM (Model-View-ViewModel) with Jetpack Compose.
- Firebase for Authentication.
- Retrofit for network calls to GitHub API.
- Paging 3 for handling pagination.
- Hilt for dependency injection.
- Coroutines for background tasks.
- StateFlow/LiveData for reactive UI updates.
#

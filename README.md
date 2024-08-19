# GitExplore

this app authenticate users with Email/Password. Also, the App allows the user to Login/Register using his/her basic info.
Note: Use Firebase Auth
The app lists all public repos using GitHub API. Also, the app have a search feature to allow the user to search for a repo using repo name. Also, the app take care of pagination and handle any network error.

#

### Project Architecture:
- MVVM (Model-View-ViewModel) with Jetpack Compose.
- Firebase for Authentication.
- Retrofit for network calls to GitHub API.
- Paging 3 for handling pagination.
- Hilt for dependency injection.
- Coroutines for background tasks.
- StateFlow/LiveData for reactive UI updates.

# 

### Project Structure:

<img  src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/project%20structure.png">


## Getting Started


### Prerequisites
- Android Studio
- An active Firebase project
- A GitHub API token for accessing repository data

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/fady0bassem/GitExplore.git
2. Open the project in Android Studio.

3. Add your Firebase credentials and GitHub API token to the appropriate configuration files.

4. Sync your project with Gradle files and build the project.

### Usage
1. Run the application on an Android device or emulator.
2. Register or log in using your email and password.
3. Browse and search for public GitHub repositories.


## Screenshots

### Splash Screen: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
   <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/1.png" width="200" alt="Screenshot 1" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;"> 
</div>

### Login Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/2.png" width="200" alt="Screenshot 2" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/3.png" width="200" alt="Screenshot 3" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
</div>

### Forgot Password Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
   <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/4.png" width="200" alt="Screenshot 4" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/5.png" width="200" alt="Screenshot 5" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
</div>

### Register Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
   <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/6.png" width="200" alt="Screenshot 6" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/7.png" width="200" alt="Screenshot 7" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
</div>

### Home Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
    <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/8.png" width="200" alt="Screenshot 8" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/9.png" width="200" alt="Screenshot 9" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
   <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/10.png" width="200" alt="Screenshot 10" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/11.png" width="200" alt="Screenshot 11" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
</div>

### Repository Details Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/12.png" width="200" alt="Screenshot 12" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">

</div>

### Account Settings Screens: 

<div style="display: flex; flex-wrap: wrap; gap: 5px;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/13.png" width="200" alt="Screenshot 13" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
  <img src="https://github.com/fady0bassem/GitExplore/blob/main/screenshots/14.png" width="200" alt="Screenshot 14" style="flex: 1 1 calc(33.333% - 10px); max-width: 100%;">
</div>


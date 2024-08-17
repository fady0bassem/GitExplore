package com.fadybassem.gitexplore.presentation.navigation.authentication

sealed class AuthenticationRoutes(val route: String) {
    data object Login : AuthenticationRoutes("login_screen")
    data object Register : AuthenticationRoutes("register_screen")
    data object ForgotPassword : AuthenticationRoutes("forgot_password_screen")
    data object CompleteProfile : AuthenticationRoutes("complete_profile_screen")
}

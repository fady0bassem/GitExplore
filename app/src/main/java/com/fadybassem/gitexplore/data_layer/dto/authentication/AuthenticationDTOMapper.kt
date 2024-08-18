package com.fadybassem.gitexplore.data_layer.dto.authentication

import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticationDTOMapper @Inject constructor(private val preferenceHelper: PreferenceHelper){

    fun mapToDomain(userResponse: User): User {
        return User(
            uid = userResponse.uid,
            email = userResponse.email,
            displayName = userResponse.displayName
        )
    }

    fun mapFromFirebaseUser(firebaseUser: FirebaseUser): User {
        return User(
            uid = firebaseUser.uid,
            providerId = firebaseUser.providerId,
            displayName = firebaseUser.displayName,
            email = firebaseUser.email,
            phone = firebaseUser.phoneNumber,
            isEmailVerified = firebaseUser.isEmailVerified,
            photoUrl = firebaseUser.photoUrl?.path
        )
    }

}
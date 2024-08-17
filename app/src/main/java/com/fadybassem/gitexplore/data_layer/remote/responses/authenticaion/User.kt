package com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class User(
    var uid: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var dateOfBirth: String? = null,
    var gender: String? = null,
    var displayName: String? = null,
    var about: String? = null,
    var profilePicture: String? = null,
    var userType: String? = null,
    var deviceToken: String? = null,
    var appVersion: String? = null,
    var platform: String? = null,
    var active: Boolean? = null,
    var firstLogin: Boolean? = null,
    var lastLogin: Date? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
) : Parcelable
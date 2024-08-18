package com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class User(
    var uid: String? = null,
    var providerId: String? = null,
    var displayName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var isEmailVerified: Boolean? = null,
    var photoUrl: String? = null,
) : Parcelable
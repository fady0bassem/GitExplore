package com.fadybassem.gitexplore.data_layer.remote.requests.authentication

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRequestModel(
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
) : Parcelable {
    fun getDisplayName(): String {
        return "$firstName $lastName"
    }
}
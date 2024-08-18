package com.fadybassem.gitexplore.data_layer.remote.responses.github

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
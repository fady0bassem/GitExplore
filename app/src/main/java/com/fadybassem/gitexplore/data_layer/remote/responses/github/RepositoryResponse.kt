package com.fadybassem.gitexplore.data_layer.remote.responses.github

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("owner") val owner: OwnerResponse,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int,
)
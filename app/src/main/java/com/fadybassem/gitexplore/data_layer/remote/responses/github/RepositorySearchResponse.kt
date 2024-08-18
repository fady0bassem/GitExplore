package com.fadybassem.gitexplore.data_layer.remote.responses.github

import com.google.gson.annotations.SerializedName

data class RepositorySearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<RepositoryResponse>,
)
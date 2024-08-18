package com.fadybassem.gitexplore.data_layer.models.github

import com.google.gson.annotations.SerializedName

data class RepositorySearch(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<Repository>,
)
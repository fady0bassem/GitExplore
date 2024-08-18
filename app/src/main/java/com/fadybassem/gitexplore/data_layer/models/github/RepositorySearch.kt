package com.fadybassem.gitexplore.data_layer.models.github

data class RepositorySearch(
    val totalCount: Int,
    val items: List<Repository>,
)
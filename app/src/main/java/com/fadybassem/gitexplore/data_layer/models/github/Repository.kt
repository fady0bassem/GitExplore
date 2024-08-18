package com.fadybassem.gitexplore.data_layer.models.github

data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val description: String?,
    val stargazersCount: Int,
)
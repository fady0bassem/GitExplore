package com.fadybassem.gitexplore.data_layer.dto.github

import com.fadybassem.gitexplore.data_layer.models.github.Owner
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.responses.github.OwnerResponse
import com.fadybassem.gitexplore.data_layer.remote.responses.github.RepositoryResponse
import javax.inject.Inject

class GithubDTOMapper @Inject constructor() {

    fun mapPublicRepositories(response: List<RepositoryResponse>): ArrayList<Repository> {
        val list = arrayListOf<Repository>()
        response.forEach { list.add(getRepository(it)) }
        return list
    }

    private fun getRepository(repository: RepositoryResponse): Repository {
        return Repository(
            id = repository.id,
            name = repository.name,
            fullName = repository.fullName,
            owner = getOwner(repository.owner),
            description = repository.description,
            stargazersCount = repository.stargazersCount
        )
    }

    private fun getOwner(owner: OwnerResponse): Owner {
        return Owner(login = owner.login, avatarUrl = owner.avatarUrl)
    }
}
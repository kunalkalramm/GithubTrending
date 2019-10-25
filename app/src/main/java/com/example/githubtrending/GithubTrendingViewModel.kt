package com.example.githubtrending

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrending.repository.BaseRepository
import com.example.githubtrending.repository.GithubDatabaseNetworkRepository
import com.example.githubtrending.repository.GithubDatabaseSqlRepository
import kotlinx.coroutines.launch

class GithubTrendingViewModel : ViewModel() {

    private val baseRepository = BaseRepository(
        GithubDatabaseSqlRepository(GithubRepositoryDatabase.getGithubRepositoryDatabase()!!),
        GithubDatabaseNetworkRepository.provideFetchRepoService()
    )

    val repositoriesLiveData =
        Transformations.map(baseRepository.fetchRepositoryData()) { liveData ->
            liveData.map {
                it.toViewModelRepositoryModel()
            }
        }

    init {
        viewModelScope.launch {
            baseRepository.getRepositoriesFromFetchRepoService()
        }
    }

    private fun RoomGithubRepositoryModel.toViewModelRepositoryModel(): ViewModelRepositoryModel {
        return ViewModelRepositoryModel(
            author = this.author,
            repoName = this.repoName,
            language = this.language,
            languageColor = this.languageColor,
            description = this.description,
            forks = this.forks,
            stars = this.stars,
            repoURL = this.repoURL,
            avatarURL = this.avatarURL
        )
    }
}
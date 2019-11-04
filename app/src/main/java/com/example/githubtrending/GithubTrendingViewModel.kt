package com.example.githubtrending

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrending.database.GithubRepositoryDatabase
import com.example.githubtrending.models.RepositoryModel
import com.example.githubtrending.models.RoomModel
import com.example.githubtrending.networkService.ApiCallResult
import com.example.githubtrending.repository.TopRepository
import com.example.githubtrending.repository.GithubDatabaseNetworkRepository
import com.example.githubtrending.repository.GithubDatabaseSqlRepository
import kotlinx.coroutines.launch

class GithubTrendingViewModel : ViewModel() {

    companion object {
        const val DEFAULT_TEXT = ""
        const val DEFAULT_NUMBER = 0
        const val DEFAULT_COLOR_HEX = "#0000ff"  //Blue colour
    }

    private val repository = TopRepository(
        GithubDatabaseSqlRepository(GithubRepositoryDatabase.getGithubRepositoryDatabase()!!),
        GithubDatabaseNetworkRepository.provideFetchRepoService()
    )
    val repositoriesLiveData =
        Transformations.map(repository.fetchRepositoryData()) { liveData ->
            liveData.map {
                it.toViewModelRepositoryModel()
            }
        }

    init {
        viewModelScope.launch {
            if(repository.getRepositoriesFromFetchRepoService() is ApiCallResult.Failure) {
                Log.d("kalrk-test", "Could not fetch data from API")
            }
        }
    }

    private fun RoomModel.toViewModelRepositoryModel(): RepositoryModel {
        return RepositoryModel(
            author = this.author ?: DEFAULT_TEXT,
            repoName = this.repoName,
            language = this.language ?: DEFAULT_TEXT,
            languageColor = this.languageColor ?: DEFAULT_COLOR_HEX,
            description = this.description ?: DEFAULT_TEXT,
            forks = this.forks ?: DEFAULT_NUMBER,
            stars = this.stars ?: DEFAULT_NUMBER,
            repoURL = this.repoURL,
            avatarURL = this.avatarURL
        )
    }
}
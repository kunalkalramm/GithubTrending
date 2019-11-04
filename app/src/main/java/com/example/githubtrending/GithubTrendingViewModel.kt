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
import org.koin.core.KoinComponent

class GithubTrendingViewModel(
    repository: TopRepository
) : ViewModel(), KoinComponent {

    companion object {
        const val DEFAULT_TEXT = ""
        const val DEFAULT_NUMBER = 0
        const val DEFAULT_COLOR_HEX = "#0000ff"  //Blue colour

        val TAG = this::class.java.name
    }

    val repositoriesLiveData =
        Transformations.map(repository.fetchRepositoryData()) { liveData ->
            liveData.map {
                it.toViewModelRepositoryModel()
            }
        }

    init {
        viewModelScope.launch {
            if(repository.getRepositoriesFromFetchRepoService() is ApiCallResult.Failure) {
                Log.d(TAG, "Could not fetch data from the repository")
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
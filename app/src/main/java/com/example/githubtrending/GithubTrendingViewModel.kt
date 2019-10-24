package com.example.githubtrending

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrending.repository.BaseRepository
import com.example.githubtrending.repository.GithubDatabaseNetworkRepository
import com.example.githubtrending.repository.GithubDatabaseSqlRepository
import kotlinx.coroutines.launch

class GithubTrendingViewModel(application: Application) : ViewModel() {

    private val baseRepository = BaseRepository(
        GithubDatabaseSqlRepository(GithubRepositoryDatabase.getGithubRepositoryDatabase(application)!!),
        GithubDatabaseNetworkRepository.provideFetchRepoService()
    )

    val liveData = baseRepository.fetchRepositoryData()

    init {
        viewModelScope.launch {
            baseRepository.getRepositoriesFromFetchRepoService()
        }
    }
}
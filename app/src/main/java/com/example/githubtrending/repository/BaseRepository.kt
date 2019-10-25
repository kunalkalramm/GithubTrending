package com.example.githubtrending.repository

import androidx.lifecycle.LiveData
import com.example.githubtrending.ApiCallResult
import com.example.githubtrending.RoomGithubRepositoryModel
import com.example.githubtrending.ViewModelRepositoryModel
import com.example.githubtrending.networkService.FetchRepositoryService
import com.example.githubtrending.networkService.models.ApiGithubRepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class BaseRepository(
    private val githubDatabaseSqlRepository: GithubDatabaseSqlRepository,
    private val fetchRepositoryService: FetchRepositoryService
) {


    suspend fun getRepositoriesFromFetchRepoService(): ApiCallResult<List<ApiGithubRepositoryModel>> {
        return withContext(Dispatchers.IO) {
            val response = fetchRepositoryService.getRepositoriesAsync()
            when {
                response.isSuccessful -> {
                    response.body()?.map { it.toRoomGithubRepositoryModel() }?.let {
                        githubDatabaseSqlRepository.insertIntoDatabase(it)
                    }
                    ApiCallResult.Success(response.body()!!)
                }
                else -> {
                    ApiCallResult.Failure(IOException("Error fetching data from the API"))
                }
            }

        }
    }

    private suspend fun getRepositoriesFromDatabase(): LiveData<List<RoomGithubRepositoryModel>>? {
        return withContext(Dispatchers.IO) {
            githubDatabaseSqlRepository.getAllRepositories()
        }
    }

    private suspend fun pushDataToRepository(roomRepositoryList: List<RoomGithubRepositoryModel>) {
        githubDatabaseSqlRepository.deleteAllEntriesFromDatabase()
        githubDatabaseSqlRepository.insertIntoDatabase(roomRepositoryList)
    }


    private fun ApiGithubRepositoryModel.toRoomGithubRepositoryModel(): RoomGithubRepositoryModel {
        return RoomGithubRepositoryModel(
            author = this.author,
            repoName = this.name!!,
            language = this.language,
            languageColor = this.languageColor,
            description = this.description,
            forks = this.forks,
            stars = this.stars,
            repoURL = this.repoURL,
            avatarURL = this.avatarURL
        )
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

    fun fetchRepositoryData() = githubDatabaseSqlRepository.getAllRepositories()


}
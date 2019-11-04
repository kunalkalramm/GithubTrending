package com.example.githubtrending.repository

import com.example.githubtrending.networkService.ApiCallResult
import com.example.githubtrending.models.RoomModel
import com.example.githubtrending.networkService.IFetchRepositoryService
import com.example.githubtrending.models.ApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TopRepository(
    private val githubDatabaseSqlRepository: GithubDatabaseSqlRepository,
    private val IFetchRepositoryService: IFetchRepositoryService
) {


    suspend fun getRepositoriesFromFetchRepoService(): ApiCallResult<List<ApiModel>> {
        return withContext(Dispatchers.IO) {
            val response = IFetchRepositoryService.getRepositoriesAsync()
            when {
                response.isSuccessful -> {
                    response.body()?.map { it.toRoomGithubRepositoryModel() }?.let {
                        pushDataToRepository(it)
                    }
                    ApiCallResult.Success(response.body()!!)
                }
                else -> {
                    ApiCallResult.Failure(IOException("Error fetching data from the API"))
                }
            }

        }
    }


    private suspend fun pushDataToRepository(roomRepositoryList: List<RoomModel>) {
        githubDatabaseSqlRepository.deleteAllEntriesFromDatabase()
        githubDatabaseSqlRepository.insertIntoDatabase(roomRepositoryList)
    }


    private fun ApiModel.toRoomGithubRepositoryModel(): RoomModel {
        return RoomModel(
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



    fun fetchRepositoryData() = githubDatabaseSqlRepository.getAllRepositories()


}
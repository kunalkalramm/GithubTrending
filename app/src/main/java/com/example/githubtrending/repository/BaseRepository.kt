package com.example.githubtrending.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.githubtrending.ApiCallResult
import com.example.githubtrending.RoomGithubRepositoryModel
import com.example.githubtrending.ViewModelRepositoryModel
import com.example.githubtrending.networkService.models.ApiGithubRepositoryModel
import kotlinx.coroutines.*
import java.io.IOException

class BaseRepository(application: Application) {

    private val githubDatabaseSqlRepository = GithubDatabaseSqlRepository(application)
    private val githubDatabaseNetworkRepository = GithubDatabaseNetworkRepository
    private val baseRepositoryScope = CoroutineScope(Dispatchers.IO)

    private lateinit var apiCallResult: ApiCallResult<List<ApiGithubRepositoryModel>>

    init {
        baseRepositoryScope.launch {
            val result = getRepositoriesFromFetchRepoService()

            when (result) {
                is ApiCallResult.Success-> {
                    val roomRepositoryList: List<RoomGithubRepositoryModel> = result.data.map {
                        it.toRoomGithubRepositoryModel()
                    }.toList()
                    pushDataToRepository(roomRepositoryList)
                }

                is ApiCallResult.Failure -> {
                    // TODO: Return data from cache and pass to view model
                }
            }
        }
    }

    private suspend fun getRepositoriesFromFetchRepoService(): ApiCallResult<List<ApiGithubRepositoryModel>> {

        val fetchRepositoryService = githubDatabaseNetworkRepository.provideFetchRepoService()
        val response = withContext(Dispatchers.IO) {
            fetchRepositoryService.getRepositoriesAsync()
        }
        if (response.isSuccessful) {
            return ApiCallResult.Success(response.body()!!)
        }
        return ApiCallResult.Failure(IOException("Error fetching data from the API"))
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
            author =  this.author,
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
            author =  this.author,
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


//    suspend fun fetchRepositoryData(): LiveData<List<RoomGithubRepositoryModel>>? {
//         return scope.async(Dispatchers.IO) {
//            githubDatabaseSqlRepository.getAllRepositories()
//        }.await()
//    }

//    fun fetchData(): LiveData<List<RoomGithubRepositoryModel>>? {
//
//        val fetchRepositoryService = githubDatabaseNetworkRepository.provideFetchRepoService()
//        val listOfRepositories: LiveData<List<ApiGithubRepositoryModel>>? = null
//        try {
//            scope.launch(Dispatchers.Main) {
//                val response = async(Dispatchers.IO) { fetchRepositoryService.getRepositoriesAsync() }.await()
//                if(response.isSuccessful) {
//
//                }
//
//            }
//        } catch (networkException: NetworkErrorException) {
//
//        }
//
//    }

}
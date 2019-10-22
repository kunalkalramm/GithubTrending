package com.example.githubtrending.repository

import android.app.Application
import com.example.githubtrending.networkService.models.GithubRepositoryModel
import kotlinx.coroutines.*
import retrofit2.Response

class BaseRepository(application: Application) {

    val job = Job()
    val scope = CoroutineScope(job) + Dispatchers.Main
    private val githubDatabaseSqlRepository = GithubDatabaseSqlRepository(application)
    private val githubDatabaseNetworkRepository = GithubDatabaseNetworkRepository

    suspend fun getRepositoriesFromFetchRepoService(): Response<List<GithubRepositoryModel>> {
        return withContext(Dispatchers.IO) {
            val fetchRepoService = githubDatabaseNetworkRepository.provideFetchRepoService()
            fetchRepoService.getRepositoriesAsync()
        }

        scope.launch {
            getRepositoriesFromFetchRepoService()
        }
    }

//    suspend fun fetchRepositoryData(): LiveData<List<GithubRepository>>? {
//         return scope.async(Dispatchers.IO) {
//            githubDatabaseSqlRepository.getAllRepositories()
//        }.await()
//    }

//    fun fetchData(): LiveData<List<GithubRepository>>? {
//
//        val fetchRepositoryService = githubDatabaseNetworkRepository.provideFetchRepoService()
//        val listOfRepositories: LiveData<List<GithubRepositoryModel>>? = null
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
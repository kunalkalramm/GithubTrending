package com.example.githubtrending.networkService

import com.example.githubtrending.networkService.models.ApiGithubRepositoryModel
import retrofit2.Response
import retrofit2.http.GET

interface FetchRepositoryService {
    @GET("/")
    suspend fun getRepositoriesAsync(): Response<List<ApiGithubRepositoryModel>>
}
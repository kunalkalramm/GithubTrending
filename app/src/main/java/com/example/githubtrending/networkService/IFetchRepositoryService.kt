package com.example.githubtrending.networkService

import com.example.githubtrending.models.ApiModel
import retrofit2.Response
import retrofit2.http.GET

interface IFetchRepositoryService {
    @GET("/")
    suspend fun getRepositoriesAsync(): Response<List<ApiModel>>
}
package com.example.githubtrending.repository

import com.example.githubtrending.AppConstants
import com.example.githubtrending.networkService.FetchRepositoryService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubDatabaseNetworkRepository {

    // Replace with injection later
    companion object {
        fun provideFetchRepoService(): FetchRepositoryService {
            return Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(FetchRepositoryService::class.java)
        }
    }


}
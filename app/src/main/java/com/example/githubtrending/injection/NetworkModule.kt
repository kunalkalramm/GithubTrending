package com.example.githubtrending.injection

import com.example.githubtrending.AppConstants
import com.example.githubtrending.networkService.FetchRepositoryService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRestAdapter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideFetchRepositoryService(restAdapter: Retrofit): FetchRepositoryService {
        return restAdapter.create(FetchRepositoryService::class.java)
    }
}
package com.example.githubtrending.injection

import com.example.githubtrending.AppConstants
import com.example.githubtrending.networkService.IFetchRepositoryService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    single {
        NetworkModuleHelper.provideRestAdapter()
    }

    single {
        NetworkModuleHelper.provideFetchRepositoryService(get())
    }
}

object NetworkModuleHelper {

    fun provideRestAdapter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun provideFetchRepositoryService(restAdapter: Retrofit): IFetchRepositoryService {
        return restAdapter.create(IFetchRepositoryService::class.java)
    }

}

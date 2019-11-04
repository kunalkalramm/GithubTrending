package com.example.githubtrending.injection

import com.example.githubtrending.GithubTrendingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GithubTrendingViewModel() }
}
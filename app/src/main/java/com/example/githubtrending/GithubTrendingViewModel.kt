package com.example.githubtrending

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.githubtrending.repository.BaseRepository

class GithubTrendingViewModel(application: Application) : ViewModel() {

    private val baseRepository = BaseRepository(application)

    fun getData() {

    }
}
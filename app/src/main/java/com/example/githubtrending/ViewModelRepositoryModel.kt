package com.example.githubtrending

data class ViewModelRepositoryModel(
    val author: String?,
    val repoName: String?,
    val language: String?,
    val languageColor: String?,
    val avatarURL: String?,
    val repoURL: String?,
    val description: String?,
    val stars: Int?,
    val forks: Int?
)
package com.example.githubtrending.networkService.models

import com.squareup.moshi.Json

data class GithubRepositoryModel (
    @field:Json(name="author") val author: String?,
    @field:Json(name="name") val name: String?,
    @field:Json(name="avatar") val avatarURL: String?,
    @field:Json(name="url") val repoURL: String?,
    @field:Json(name="language") val language: String?,
    @field:Json(name="languageColor") val languageColor: String?,
    @field:Json(name="description") val description: String?,
    @field:Json(name="stars") val stars: Int?,
    @field:Json(name="forks") val forks: Int?
)

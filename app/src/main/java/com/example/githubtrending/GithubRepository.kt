package com.example.githubtrending

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Repository_Table")

data class GithubRepository(
    @PrimaryKey(autoGenerate = true) val repoId: Int,

    val author: String?,
    val repoName: String?,
    val language: String?,
    val languageColor: String?,
    val avatarURL: String?,
    val repoURL: String?,
    val description: String?,
    val stars: Int?,
    val forks: Int?)
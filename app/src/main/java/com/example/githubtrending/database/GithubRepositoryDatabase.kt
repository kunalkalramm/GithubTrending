package com.example.githubtrending.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubtrending.models.RoomModel

@Database(entities = [RoomModel::class], version = 1)
abstract class GithubRepositoryDatabase: RoomDatabase() {
    abstract fun getGithubRepositoryDao(): IGithubRepositoryDao
}
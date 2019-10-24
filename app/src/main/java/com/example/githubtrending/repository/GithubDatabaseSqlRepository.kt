package com.example.githubtrending.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.githubtrending.RoomGithubRepositoryModel
import com.example.githubtrending.GithubRepositoryDatabase
import com.example.githubtrending.IGithubRepositoryDao
import kotlinx.coroutines.*

class GithubDatabaseSqlRepository(application: Application) {

    private val githubRepositoryDao: IGithubRepositoryDao?
    val githubRepositoryDatabase = GithubRepositoryDatabase.getGithubRepositoryDatabase(application)

    init {
        githubRepositoryDao = githubRepositoryDatabase?.getGithubRepositoryDao()
    }

    suspend fun insertIntoDatabase(roomGithubRepositories: List<RoomGithubRepositoryModel>) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao?.insertIntoGithubRepositoryTable(roomGithubRepositories)
        }
    }

    suspend fun updateEntryInDatabase(roomGithubRepositoryModel: RoomGithubRepositoryModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao?.updateGithubRepositoryTable(roomGithubRepositoryModel)
        }
    }

    suspend fun deleteEntryFromDatabase(roomGithubRepositoryModel: RoomGithubRepositoryModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao?.deleteRepositoryFromGithubRepository(roomGithubRepositoryModel)
        }
    }

    suspend fun deleteAllEntriesFromDatabase() {
        withContext(Dispatchers.IO) {
            githubRepositoryDao?.deleteAllFromGithubRepository()
        }
    }

    suspend fun getAllRepositories(): LiveData<List<RoomGithubRepositoryModel>>? {
        return withContext(Dispatchers.IO) {
            githubRepositoryDao?.getAllRepositories()
        }
    }
}
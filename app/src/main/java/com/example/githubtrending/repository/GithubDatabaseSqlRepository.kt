package com.example.githubtrending.repository

import androidx.lifecycle.LiveData
import com.example.githubtrending.GithubRepositoryDatabase
import com.example.githubtrending.IGithubRepositoryDao
import com.example.githubtrending.RoomGithubRepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubDatabaseSqlRepository(githubRepositoryDatabase: GithubRepositoryDatabase) {

    private val githubRepositoryDao: IGithubRepositoryDao =
        githubRepositoryDatabase.getGithubRepositoryDao()


    suspend fun insertIntoDatabase(roomGithubRepositories: List<RoomGithubRepositoryModel>) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.insertIntoGithubRepositoryTable(roomGithubRepositories)
        }
    }

    suspend fun updateEntryInDatabase(roomGithubRepositoryModel: RoomGithubRepositoryModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.updateGithubRepositoryTable(roomGithubRepositoryModel)
        }
    }

    suspend fun deleteEntryFromDatabase(roomGithubRepositoryModel: RoomGithubRepositoryModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.deleteRepositoryFromGithubRepository(roomGithubRepositoryModel)
        }
    }

    suspend fun deleteAllEntriesFromDatabase() {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.deleteAllFromGithubRepository()
        }
    }

    fun getAllRepositories(): LiveData<List<RoomGithubRepositoryModel>> {
        return githubRepositoryDao.getAllRepositories()
    }
}
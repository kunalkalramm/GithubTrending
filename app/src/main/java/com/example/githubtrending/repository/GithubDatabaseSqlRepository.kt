package com.example.githubtrending.repository

import com.example.githubtrending.database.GithubRepositoryDatabase
import com.example.githubtrending.database.IGithubRepositoryDao
import com.example.githubtrending.models.RoomModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubDatabaseSqlRepository(githubRepositoryDatabase: GithubRepositoryDatabase) {

    private val githubRepositoryDao: IGithubRepositoryDao =
        githubRepositoryDatabase.getGithubRepositoryDao()


    suspend fun insertIntoDatabase(rooms: List<RoomModel>) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.insertIntoGithubRepositoryTable(rooms)
        }
    }

    suspend fun updateEntryInDatabase(roomModel: RoomModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.updateGithubRepositoryTable(roomModel)
        }
    }

    suspend fun deleteEntryFromDatabase(roomModel: RoomModel) {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.deleteRepositoryFromGithubRepository(roomModel)
        }
    }

    suspend fun deleteAllEntriesFromDatabase() {
        withContext(Dispatchers.IO) {
            githubRepositoryDao.deleteAllFromGithubRepository()
        }
    }

    fun getAllRepositories(): List<RoomModel> {
        return githubRepositoryDao.getAllRepositories()
    }
}
package com.example.githubtrending.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubtrending.GithubRepository
import com.example.githubtrending.GithubRepositoryDatabase
import com.example.githubtrending.IGithubRepositoryDao
import kotlinx.coroutines.*

class GithubDatabaseSqlRepository(application: Application) {

    private val githubRepositoryDao: IGithubRepositoryDao?
        val githubRepositoryDatabase = GithubRepositoryDatabase.getGithubRepositoryDatabase(application)

    init {
        githubRepositoryDao = githubRepositoryDatabase?.getGithubRepositoryDao()
    }

    fun insertIntoDatabase(githubRepository: GithubRepository): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            githubRepositoryDao?.insertIntoGithubRepositoryTable(githubRepository)
        }
    }

    fun updateEntryInDatabase(githubRepository: GithubRepository):Job {
        return GlobalScope.launch(Dispatchers.IO) {
            githubRepositoryDao?.updateGithubRepositoryTable(githubRepository)
        }
    }

    fun deleteEntryFromDatabase(githubRepository: GithubRepository): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            githubRepositoryDao?.deleteRepositoryFromGithubRepository(githubRepository)
        }
    }

    fun deleteAllEntriesFromDatabase(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            githubRepositoryDao?.deleteAllFromGithubRepository()
        }
    }

    fun getAllRepositories(): LiveData<List<GithubRepository>>? {
        return githubRepositoryDao?.getAllRepositories()
    }
}